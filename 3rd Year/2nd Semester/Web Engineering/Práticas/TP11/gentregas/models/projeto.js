var mongoose = require("mongoose")

var projetoSchema = new mongoose.Schema({
    _id : String, 
    dataCriacao: String,
    dataEntrega: String, 
    ano: String,
    uc: String,
    titulo : String, 
    resumo: String,
    enunciado: String
}, { versionKey: false })

module.exports = mongoose.model('projeto', projetoSchema, 'projetos')
