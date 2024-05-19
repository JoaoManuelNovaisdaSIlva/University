var mongoose = require('mongoose');

var plantaSchema = new mongoose.Schema({
    _id: Number,
    numeroDeRegisto: { type: Number, alias: 'Número de Registo' },
    codigoDeRua: { type: Number, alias: 'Código de rua' },
    rua: String,
    local: String,
    freguesia: String,
    especie: { type: String, alias: 'Espécie' },  // Now using an alias to match the DB field
    nomeCientifico: { type: String, alias: 'Nome Científico' },
    origem: String,
    dataDePlantacao: { type: String, alias: 'Data de Plantação' },
    estado: String,
    caldeira: { type: String, alias: 'Caldeira' },
    tutor: { type: String, alias: 'Tutor' },
    implantacao: { type: String, alias: 'Implantação' },
    gestor: { type: String, alias: 'Gestor' },
    dataDeAtualizacao: { type: String, alias: 'Data de actualização' },
    numeroDeIntervencoes: { type: Number, alias: 'Número de intervenções' }
}, { strict: false });

module.exports = mongoose.model('Planta', plantaSchema, 'plantas');
