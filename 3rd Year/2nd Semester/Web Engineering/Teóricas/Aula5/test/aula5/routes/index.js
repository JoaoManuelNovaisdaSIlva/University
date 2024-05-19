var express = require('express');
var router = express.Router();
var axios = require('axios')

/* GET home page. */
router.get('/', function(req, res, next) { // next é uma estrutura de dados que pode ser passada
  var d = new Date().toISOString().substring(0, 16);
  res.render('index', { title: 'EngWeb2024::Aula Teórica 5', data: d });
});

router.get('/tp', (req, res) =>{
  var d = new Date().toISOString().substring(0, 16);
  const graphDbEndpoint = 'http://epl.di.uminho.pt:7200/repositories/tab_periodica';
  const sparqlQuery = `
  PREFIX tp: <http://www.daml.org/2003/01/periodictable/PeriodicTable#>
  select ?na ?nome where {
      ?s a tp:Element .
      ?s tp:name ?nome .
      ?s tp:atomicNumber ?na .
  } 
  `;
  const headers = {
    'Content-Type': 'application/x-www-form-urlencoded',
    'Accept': 'application/sparql-results+json',
  };
  const queryString = `query=${encodeURIComponent(sparqlQuery)}`;

  axios.get(`${graphDbEndpoint}?${queryString}`, null, { headers })
    .then(response => {
      var lista = response.data.results.bindings
      var elems=[]
      lista.forEach(element => {
        elems.push({'na' : element.na.value, 'nome' : element.nome.value})
      });
      res.render('tp', {lista : elems, data : d, title : "Tabela Periódica do Elementos"})
    })
    .catch(error => {
      res.render('error', {error, error, message : "Não consegui aceder ao endpoint..."})
    });
})

module.exports = router;
