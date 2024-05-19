var express = require('express');
var router = express.Router();
var jsonfile = require('jsonfile');
var fs = require('fs');
var multer = require('multer');
var upload = multer({dest: 'uploads'})

/* GET home page. */
router.get('/', function(req, res) {
  var d = new Date().toISOString().substring(0, 19);
  jsonfile.readFile(__dirname + '/../data/dbFiles.json', (error, fileList) => {
    if(error){
      res.render('error', {error: error})
    }
    else{
      res.render('index', {files : fileList, date : d});
    }
  });
});

/* File submition */
router.post('/files', upload.single('myFile'), (req, res) => {
  console.log('cdir: ' + __dirname)
  let oldPath = __dirname + '/../' + req.file.path;
  console.log("old: " + oldPath)
  let newPath = __dirname + '/../public/fileStore/' + req.file.originalname
  console.log("new: " + newPath)
  
  fs.rename(oldPath, newPath, function(error){
    if(error) throw error
  })

  var d = new Date().toISOString().substring(0, 19);
  var files = jsonfile.readFileSync(__dirname + '/../data/dbFiles.json')
  files.push({
    date : d,
    name : req.file.originalname,
    type : req.file.mimetype,
    size : req.file.size
  })
  jsonfile.writeFileSync(__dirname + '/../data/dbFiles.json', files)
  res.redirect('/')
});


router.get('/download/:name', (req, res) => {
  console.log(__dirname + '/../public/fileStore/' + req.params.name)
  res.download(__dirname + '/../public/fileStore/' + req.params.name)
});
module.exports = router;
