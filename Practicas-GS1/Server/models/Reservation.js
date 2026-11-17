const mongoose = require('mongoose');

const ReservationSchema = new mongoose.Schema({
    // DATOS DEL CONTACTO
    nombre_cliente: {
        type: String,
        required: true
    },
    email_cliente: {
        type: String,
        required: true
    },
    telefono_cliente: {
        type: String,
        required: true
    },

    // LISTA DE PRODUCTOS (Carrito)
    items: [{
        producto_id: {
            type: mongoose.Schema.Types.ObjectId,
            ref: 'Product',
            required: true
        },
        nombre_producto: {
            type: String,
            required: true
        },
        cantidad: {
            type: Number,
            required: true,
            min: 1
        },
        precio_unitario: {
            type: Number,
            required: true
        }
    }],

    // TOTALES Y LOGÍSTICA
    total_reserva: {
        type: Number,
        required: true
    },
    fecha_recogida: {
        type: Date,
        required: true
    },
    estado: {
        type: String,
        enum: ['pendiente', 'listo para recoger', 'finalizado'],
        default: 'pendiente'
    },
    fecha_creacion: {
        type: Date,
        default: Date.now
    }
});

module.exports = mongoose.model('Reservation', ReservationSchema);
