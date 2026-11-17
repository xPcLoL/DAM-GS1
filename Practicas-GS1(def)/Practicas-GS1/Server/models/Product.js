const mongoose = require('mongoose');

const ProductSchema = new mongoose.Schema({
    nombre: {
        type: String,
        required: true
    },
    precio: {
        type: Number,
        required: true
    },
    imagen_url: {
        type: String,
        default: ''
    },
    tipo: {
        type: String,
        default: ''
    },
    categoria_especial: {
        type: String,
        default: 'no'
    },
    descripcion: {
        type: String,
        default: ''
    },
    limite_compra: {
        type: Number,
        default: null
    }
});

module.exports = mongoose.model('Product', ProductSchema);
