const { spawn } = require('child_process');
const fs = require('fs');
const path = require('path');

const logFile = "C:\\Users\\Usuario\\Documents\\ROBLOX_NUEVO_PROYECTO\\mcp-debug.log";
function log(msg) {
  try {
    fs.appendFileSync(logFile, `[${new Date().toISOString()}] ${msg}\n`);
  } catch (e) {}
}

function getStudioPath() {
  const versionsDir = "C:\\Users\\Usuario\\AppData\\Local\\Roblox\\Versions";
  try {
    const entries = fs.readdirSync(versionsDir);
    for (const entry of entries) {
      const candidate = path.join(versionsDir, entry, "StudioMCP.exe");
      if (fs.existsSync(candidate)) {
        return candidate;
      }
    }
  } catch (e) {}
  return "C:\\Users\\Usuario\\AppData\\Local\\Roblox\\Versions\\version-7aaa6838d36c4448\\StudioMCP.exe";
}

const studioPath = getStudioPath();
log(`Starting StudioMCP: ${studioPath}`);

const studio = spawn(studioPath, [], { stdio: ['pipe', 'pipe', 'pipe'] });

studio.on('error', (err) => {
  log(`studio error: ${err.message}`);
  process.exit(1);
});

studio.stderr.on('data', (chunk) => {
  log(`studio stderr: ${chunk.toString()}`);
});

process.stdin.on('data', (chunk) => {
  const str = chunk.toString();
  log(`stdin: ${str}`);

  // Si Antigravity envía "server/discover"
  if (str.includes('server/discover')) {
    const idMatch = str.match(/"id"\s*:\s*(\d+|"[^"]+")/);
    const id = idMatch ? JSON.parse(idMatch[1]) : 1;

    const resObj = { jsonrpc: "2.0", id: id, result: {} };
    process.stdout.write(JSON.stringify(resObj) + '\n');
    log(`replied to server/discover with id ${id}`);
    return;
  }

  studio.stdin.write(chunk);
});

studio.stdout.on('data', (chunk) => {
  log(`stdout: ${chunk.toString()}`);
  process.stdout.write(chunk);
});

studio.on('exit', (code, signal) => {
  log(`studio exited with code ${code}, signal ${signal}`);
  process.exit(code || 0);
});
