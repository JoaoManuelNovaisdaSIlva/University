const mongoose = require('mongoose')
var Projeto = require("../models/projeto")

module.exports.list = () => {
    return Projeto
        .find()
        .sort({dataEntrega : 1})
        .exec()
}

module.exports.findById = id => {
    return Projeto
        .findOne({_id : id})
        .exec()
}

module.exports.insert = projeto => {
    if((Projeto.find({_id : projeto._id}).exec()).length != 1){
        var newProjeto = new Projeto(projeto)
        return newProjeto.save()
    }
}

module.exports.update = (id, proj) => {
    return Projeto
        .findByIdAndUpdate(id, proj, {new : true})
}

module.exports.remove = id => {
    return Projeto
        .findOneAndDelete({_id : id})
}