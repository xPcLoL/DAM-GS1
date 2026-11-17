const express = require('express');
const router = express.Router();
const nodemailer = require('nodemailer');
const Reservation = require('../models/Reservation');
const path = require('path');
const fs = require('fs');

// POST /api/reservations - Crear nueva reserva
router.post('/', async (req, res) => {
    try {
        const {
            nombre_cliente,
            email_cliente,
            telefono_cliente,
            fecha_recogida,
            items,
            total_reserva
        } = req.body;

        // Validar fecha (Backend)
        const fechaObj = fecha_recogida ? new Date(fecha_recogida) : new Date(Date.now() + 24 * 60 * 60 * 1000);
        const hoy = new Date();
        hoy.setHours(0, 0, 0, 0);
        if (fechaObj < hoy) {
            return res.status(400).json({ error: "La fecha de recogida no puede ser anterior a hoy." });
        }

        // 1. Guardar en Base de Datos
        const newReservation = new Reservation({
            nombre_cliente,
            email_cliente,
            telefono_cliente,
            items,
            total_reserva,
            fecha_recogida: fechaObj
        });

        const savedReservation = await newReservation.save();

        // 2. Configurar Email (Nodemailer)
        // IMPORTANTE: Asegúrate de tener EMAIL_USER y EMAIL_PASS en .env
        const transporter = nodemailer.createTransport({
            service: 'gmail',
            auth: {
                user: process.env.EMAIL_USER,
                pass: process.env.EMAIL_PASS
            }
        });

        // Contenido HTML del correo con estilos
        const itemsHtml = items.map(item => `
            <li style="margin-bottom: 10px; padding-bottom: 10px; border-bottom: 1px solid #ddd;">
                <span style="font-weight: bold; color: #234260;">${item.nombre_producto}</span><br>
                <span style="color: #555;">${item.cantidad} x ${item.precio_unitario.toFixed(2)}€</span>
            </li>
        `).join('');

        const logoPath = path.join(__dirname, '../../Cliente/imagenes/tienda_png/ISOTIPO 1.png');

        // Formato de fecha legible
        const fechaLegible = newReservation.fecha_recogida.toLocaleDateString('es-ES', {
            weekday: 'long', year: 'numeric', month: 'long', day: 'numeric'
        });

        const mailOptions = {
            from: process.env.EMAIL_USER,
            to: ['andresivan200015@gmail.com', email_cliente],
            subject: `Recibo Reserva Harmelys (${fechaLegible})`,
            html: `
            <div style="font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; max-width: 600px; margin: 0 auto; background-color: #EDDEC8; color: #234260; border-radius: 8px; overflow: hidden;">
                <!-- Header -->
                <div style="background-color: #234260; padding: 20px; text-align: center;">
                    <img src="cid:logo_harmelys" alt="Harmelys Logo" style="max-height: 80px; width: auto;">
                    <h1 style="color: #EDDEC8; margin: 10px 0 0; font-family: 'Times New Roman', serif; letter-spacing: 2px;">HARMÉLYS</h1>
                    <p style="color: #EDDEC8; margin: 5px 0 0; font-size: 14px; text-transform: uppercase; letter-spacing: 1px;">Confirmación de Reserva</p>
                </div>
                
                <!-- Body -->
                <div style="padding: 30px;">
                    <p>Hola <strong>${nombre_cliente}</strong>,</p>
                    <p>Hemos recibido correctamente tu solicitud de reserva. Aquí tienes los detalles:</p>
                    
                    <div style="background-color: #fff; padding: 20px; border-radius: 8px; margin: 20px 0; box-shadow: 0 2px 5px rgba(0,0,0,0.1);">
                        <h3 style="color: #234260; margin-top: 0; border-bottom: 2px solid #234260; padding-bottom: 5px;">Detalles del Pedido</h3>
                        <ul style="list-style: none; padding: 0;">
                            ${itemsHtml}
                        </ul>
                        <p style="text-align: right; font-size: 18px; font-weight: bold; margin-top: 20px; color: #234260;">Total: ${total_reserva.toFixed(2)}€</p>
                        <p style="text-align: right; font-size: 14px; color: #666; margin-top: 5px;">ID Reserva: ${savedReservation._id}</p>
                    </div>

                    <div style="background-color: #e3d5c0; padding: 15px; border-radius: 8px; margin-bottom: 20px;">
                        <h4 style="margin: 0 0 10px; color: #234260;">Información de Recogida</h4>
                        <p style="margin: 5px 0;"><strong>Fecha:</strong> ${fechaLegible}</p>
                        <p style="margin: 5px 0;"><strong>Dirección:</strong> Calle de la Masa Madre 19, 28000 Madrid</p>
                        <p style="margin: 5px 0;"><strong>Teléfono:</strong> +34 91 123 45 67</p>
                    </div>

                    <p style="font-size: 12px; color: #666; text-align: center; margin-top: 30px;">
                        Gracias por confiar en Harmélys.<br>
                        Este es un correo automático, por favor no respondas a esta dirección.
                    </p>
                </div>
                
                <!-- Footer -->
                <div style="background-color: #234260; color: #EDDEC8; padding: 10px; text-align: center; font-size: 12px;">
                    &copy; ${new Date().getFullYear()} Panadería & Pastelería Harmélys
                </div>
            </div>
            `,
            attachments: [{
                filename: 'ISOTIPO 1.png',
                path: logoPath,
                cid: 'logo_harmelys' // same cid value as in the html img src
            }]
        };

        // 3. Enviar Email
        try {
            await transporter.sendMail(mailOptions);
            console.log('Email enviado correctamente');
        } catch (emailError) {
            console.error('Error enviando email:', emailError);
            // No fallamos la request si el email falla, pero lo logueamos
        }

        res.status(201).json({
            message: "Reserva creada con éxito",
            reservation: savedReservation
        });

    } catch (err) {
        console.error("Error al crear reserva:", err);
        res.status(500).json({ error: "Error al procesar la reserva" });
    }
});

// GET /api/reservations - Obtener todas las reservas (para admin)
router.get('/', async (req, res) => {
    try {
        // Podríamos filtrar por query params, pero por ahora devolvemos todas
        // Ordenar por fecha de creación descendente
        const reservations = await Reservation.find().sort({ fecha_creacion: -1 });
        res.json(reservations);
    } catch (err) {
        console.error("Error obteniendo reservas:", err);
        res.status(500).json({ error: "Error al obtener reservas" });
    }
});

// PATCH /api/reservations/:id/status - Actualizar estado
router.patch('/:id/status', async (req, res) => {
    try {
        const { estado } = req.body;
        // Validar estado
        if (!['pendiente', 'listo para recoger', 'finalizado'].includes(estado)) {
            return res.status(400).json({ error: "Estado inválido" });
        }

        const updatedReservation = await Reservation.findByIdAndUpdate(
            req.params.id,
            { estado: estado },
            { new: true }
        );

        if (!updatedReservation) {
            return res.status(404).json({ error: "Reserva no encontrada" });
        }

        res.json(updatedReservation);
    } catch (err) {
        console.error("Error actualizando reserva:", err);
        res.status(500).json({ error: "Error al actualizar reserva" });
    }
});

// DELETE /api/reservations/:id - Eliminar reserva
router.delete('/:id', async (req, res) => {
    try {
        const deletedReservation = await Reservation.findByIdAndDelete(req.params.id);
        if (!deletedReservation) {
            return res.status(404).json({ error: "Reserva no encontrada" });
        }
        res.json({ message: "Reserva eliminada correctamente" });
    } catch (err) {
        console.error("Error eliminando reserva:", err);
        res.status(500).json({ error: "Error al eliminar reserva" });
    }
});

module.exports = router;
