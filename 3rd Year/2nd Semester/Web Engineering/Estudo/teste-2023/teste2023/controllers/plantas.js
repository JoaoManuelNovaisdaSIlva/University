var Planta = require("../models/planta")

module.exports.list = () => {
    return Planta
        .find()
        .sort({_id : 1})
        .exec()
}

module.exports.findByID = idReq => {
    return Planta
        .findOne({_id : idReq})
        .exec()
}

module.exports.findByEspecie = especieReq => {
    return Planta
        .find({Espécie: especieReq})
        .exec()
}

module.exports.findByImplant = implantReq => {
    return Planta
        .find({implantacao : implantReq})
        .exec()
}

module.exports.findFreguesias = () => {
    return Planta
        .distinct('Freguesia')
        .sort()
        .exec()
}

module.exports.findEspecies = () => {
    return Planta
        .distinct("Espécie")
        .sort()
        .exec()
}

module.exports.insert = novo => {
    if((Planta.find({_id : novo._id}).exec()).length() != 1){
        var newPlanta = new Planta(novo)
        return newPlanta.save()
    }
}

module.exports.delete = id => {
    if((Planta.find({_id : id}).exec()).length() != 1){
        return Planta
        .deleteOne({_id : id})
        .exec()
    }
}