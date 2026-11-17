require('dotenv').config();
const nodemailer = require('nodemailer');

async function testEmail() {
    console.log("Testing email configuration...");
    console.log("User:", process.env.EMAIL_USER);
    // Mascaramos la pass para el log
    const pass = process.env.EMAIL_PASS || "";
    console.log("Pass length:", pass.length);
    console.log("Pass (first 3 chars):", pass.substring(0, 3));

    const transporter = nodemailer.createTransport({
        service: 'gmail',
        auth: {
            user: process.env.EMAIL_USER,
            pass: process.env.EMAIL_PASS // Intento usarla tal cual (con espacios si los tiene)
        }
    });

    try {
        const info = await transporter.sendMail({
            from: process.env.EMAIL_USER,
            to: process.env.EMAIL_USER, // Enviar a sí mismo para probar
            subject: "Test Email from Script",
            text: "If you receive this, the configuration is correct."
        });
        console.log("Email sent successfully!", info.messageId);
    } catch (error) {
        console.error("Error sending email:", error);

        if (process.env.EMAIL_PASS && process.env.EMAIL_PASS.includes(" ")) {
            console.log("\n[TIP] Detected spaces in EMAIL_PASS. Trying again with spaces removed...");
            try {
                const transporter2 = nodemailer.createTransport({
                    service: 'gmail',
                    auth: {
                        user: process.env.EMAIL_USER,
                        pass: process.env.EMAIL_PASS.replace(/\s/g, "")
                    }
                });
                const info2 = await transporter2.sendMail({
                    from: process.env.EMAIL_USER,
                    to: process.env.EMAIL_USER,
                    subject: "Test Email (Spaces Removed)",
                    text: "If you receive this, the configuration is correct (spaces were removed)."
                });
                console.log("Email sent successfully with spaces removed!", info2.messageId);
            } catch (err2) {
                console.error("Still failed with spaces removed:", err2);
            }
        }
    }
}

testEmail();
