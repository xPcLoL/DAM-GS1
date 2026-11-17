require('dotenv').config();
const express = require('express');
const cors = require('cors');
const helmet = require('helmet');
const rateLimit = require('express-rate-limit');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');

const app = express();
const PORT = process.env.PORT || 3000;
const SECRET_KEY = process.env.SECRET_KEY || 'super_secret_key_change_me_in_production';

// Conexión a MongoDB
const mongoose = require('mongoose');
const dns = require('dns');
dns.setServers(['8.8.8.8']); // Fix para resolución DNS de registros SRV
mongoose.connect(process.env.MONGO_URI)
    .then(() => console.log('Conectado a MongoDB Atlas'))
    .catch(err => console.error('Error conectando a MongoDB:', err));

// 1. Security Headers (Helmet)
app.use(helmet());

// 2. CORS (Allow Client)
app.use(cors({
    origin: '*', // En producción, restringir a dominio específico
    methods: ['GET', 'POST', 'PUT', 'DELETE', 'PATCH']
}));

// 3. Body Parser
app.use(express.json());

// 4. Rate Limiter (Prevent Brute Force)
const loginLimiter = rateLimit({
    windowMs: 15 * 60 * 1000, // 15 minutos
    max: 5, // Limite de 5 intentos por IP
    message: { error: "Demasiados intentos. Intente nuevamente en 15 minutos." }
});

// MOCK USER DB (En memoria para demo)
// Usuario: admin, Password: 12345
// Generamos el hash al inicio para simular que viene de DB cifrada
let usersDB = [];

async function initDB() {
    const saltRounds = 10;
    const hashedPassword = await bcrypt.hash('12345', saltRounds);
    usersDB.push({
        username: 'admin',
        passwordHash: hashedPassword
    });
    console.log("Database initialized. Admin user ready.");
}
initDB();

// MIDDLEWARE AUTENTICACIÓN
function authenticateToken(req, res, next) {
    const authHeader = req.headers['authorization'];
    const token = authHeader && authHeader.split(' ')[1]; // Bearer TOKEN

    if (!token) return res.sendStatus(401); // No token

    jwt.verify(token, SECRET_KEY, (err, user) => {
        if (err) return res.sendStatus(403); // Token inválido
        req.user = user;
        next();
    });
}

// RUTAS
app.get('/', (req, res) => {
    res.send('Secure API Running');
});

// -- API PRODUCTOS (CRUD) --
const Product = require('./models/Product');

// 1. GET ALL (Público)
app.get('/api/products', async (req, res) => {
    try {
        const products = await Product.find();
        res.json(products);
    } catch (err) {
        res.status(500).json({ error: "Error al obtener productos" });
    }
});

// 2. CREATE (Protegido)
app.post('/api/products', authenticateToken, async (req, res) => {
    try {
        const newProduct = new Product(req.body);
        const savedProduct = await newProduct.save();
        res.status(201).json(savedProduct);
    } catch (err) {
        res.status(400).json({ error: "Error al crear producto", details: err.message });
    }
});

// 3. UPDATE (Protegido)
app.put('/api/products/:id', authenticateToken, async (req, res) => {
    try {
        const updatedProduct = await Product.findByIdAndUpdate(
            req.params.id,
            req.body,
            { new: true } // Devuelve el objeto modificado
        );
        if (!updatedProduct) return res.status(404).json({ error: "Producto no encontrado" });
        res.json(updatedProduct);
    } catch (err) {
        res.status(500).json({ error: "Error al actualizar producto" });
    }
});

// 4. DELETE (Protegido)
app.delete('/api/products/:id', authenticateToken, async (req, res) => {
    try {
        const deletedProduct = await Product.findByIdAndDelete(req.params.id);
        if (!deletedProduct) return res.status(404).json({ error: "Producto no encontrado" });
        res.json({ message: "Producto eliminado correctamente" });
    } catch (err) {
        res.status(500).json({ error: "Error al eliminar producto" });
    }
});

// -- API CATEGORIAS (Tipos y Especialidades) --
const Category = require('./models/Category');

// 1. GET ALL (Público, para la tienda y admin inicial)
app.get('/api/categories', async (req, res) => {
    try {
        const categories = await Category.find();
        res.json(categories);
    } catch (err) {
        res.status(500).json({ error: "Error al obtener categorías" });
    }
});

// 2. CREATE (Protegido)
app.post('/api/categories', authenticateToken, async (req, res) => {
    try {
        const newCategory = new Category(req.body);
        const savedCategory = await newCategory.save();
        res.status(201).json(savedCategory);
    } catch (err) {
        res.status(400).json({ error: "Error al crear categoría", details: err.message });
    }
});

// 4. DELETE (Protegido)
app.delete('/api/categories/:id', authenticateToken, async (req, res) => {
    try {
        const deletedCategory = await Category.findByIdAndDelete(req.params.id);
        if (!deletedCategory) return res.status(404).json({ error: "Categoría no encontrada" });
        res.json({ message: "Categoría eliminada correctamente" });
    } catch (err) {
        res.status(500).json({ error: "Error al eliminar categoría" });
    }
});

// -- API RESERVAS --
app.use('/api/reservations', require('./routes/reservations'));

app.post('/api/login', loginLimiter, async (req, res) => {
    const { username, password } = req.body;

    // 1. Validar input
    if (!username || !password) {
        return res.status(400).json({ error: "Usuario y contraseña requeridos" });
    }

    // 2. Buscar usuario
    const user = usersDB.find(u => u.username === username);
    if (!user) {
        // Retornamos genérico para no enumerar usuarios
        return res.status(401).json({ error: "Credenciales inválidas" });
    }

    // 3. Comparar contraseña (Bcrypt)
    const match = await bcrypt.compare(password, user.passwordHash);
    if (!match) {
        return res.status(401).json({ error: "Credenciales inválidas" });
    }

    // 4. Generar Token (JWT)
    const token = jwt.sign(
        { username: user.username, role: 'admin' },
        SECRET_KEY,
        { expiresIn: '1h' }
    );

    // 5. Success
    res.json({
        message: "Login exitoso",
        token: token,
        user: { username: user.username }
    });
});

// Arrancar servidor
app.listen(PORT, () => {
    console.log(`Server running on http://localhost:${PORT}`);
});
