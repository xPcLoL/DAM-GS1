require('dotenv').config();
const mongoose = require('mongoose');
const dns = require('dns');
const Product = require('./models/Product');

// Fix for SRV record resolution
dns.setServers(['8.8.8.8']);

console.log('Connecting to MongoDB...');

mongoose.connect(process.env.MONGO_URI)
    .then(async () => {
        console.log('SUCCESS: Connected to MongoDB');

        try {
            // Clean up previous test if exists
            await Product.deleteOne({ nombre: "barra de pan" });

            const newProduct = new Product({
                nombre: "barra de pan",
                precio: 1.20,
                imagen_url: "https://example.com/pan.jpg",
                tipo: "panaderia",
                descripcion: "Pan fresco del día",
                categoria_especial: "no"
            });

            const savedProduct = await newProduct.save();
            console.log('SUCCESS: Product saved successfully!');
            console.log(JSON.stringify(savedProduct, null, 2));
        } catch (error) {
            console.error('FAILURE: Error saving product', error);
        } finally {
            mongoose.disconnect();
            process.exit(0);
        }
    })
    .catch(err => {
        console.error('FAILURE: Could not connect to MongoDB.');
        console.error(err);
        process.exit(1);
    });
