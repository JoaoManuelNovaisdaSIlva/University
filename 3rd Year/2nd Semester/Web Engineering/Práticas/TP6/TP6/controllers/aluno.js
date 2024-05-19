var Aluno = require('../models/aluno')

module.exports.list = () => {
    return Aluno
        .find()
        .sort({nome : 1})
        .exec()
}

module.exports.findById = id => {
    return Aluno
        .findOne({_id : id})
        .exec()
}

module.exports.insert = aluno =>{
    if((Aluno.find({_id : aluno._id}).exec()).length != 1){
        var newAluno = new Aluno(aluno)
        return newAluno.save()
    }
}