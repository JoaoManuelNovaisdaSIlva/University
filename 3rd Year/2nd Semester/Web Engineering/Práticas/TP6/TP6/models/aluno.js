var mongoose = require("mongoose")

var alunoSchema = new mongoose.Schema({
    _id : String,
    nome : String,
    gitlink : String,
    tp1 : Boolean,
    tp2 : Boolean,
    tp3 : Boolean,
    tp4 : Boolean,
    tp5 : Boolean,
    tp6 : Boolean,
    tp7 : Boolean,
    tp8 : Boolean,
    teste : Number,
    projeto : Number
}, {versionKey : false})

module.exports = mongoose.model('aluno', alunoSchema)