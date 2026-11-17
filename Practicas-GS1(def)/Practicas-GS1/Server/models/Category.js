const mongoose = require('mongoose');

const CategorySchema = new mongoose.Schema({
    name: {
        type: String,
        required: true
    },
    kind: {
        type: String,
        enum: ['type', 'specialty'],
        required: true
    }
});

// Composite unique index so we can't have duplicate names within the same kind
CategorySchema.index({ name: 1, kind: 1 }, { unique: true });

module.exports = mongoose.model('Category', CategorySchema);
