const axios = require('axios');

// Define your GraphDB endpoint URL
const graphDbEndpoint = 'http://epl.di.uminho.pt:7200/repositories/tab_periodica';

// Define your SPARQL query
const sparqlQuery = `
PREFIX tp: <http://www.daml.org/2003/01/periodictable/PeriodicTable#>
select ?na ?nome where {
    ?s a tp:Element .
    ?s tp:name ?nome .
    ?s tp:atomicNumber ?na .
} 
`;

// Define the HTTP headers
const headers = {
  'Content-Type': 'application/x-www-form-urlencoded',
  'Accept': 'application/sparql-results+json',
};

// Create the URL-encoded query string
const queryString = `query=${encodeURIComponent(sparqlQuery)}`;

// Send the SPARQL query using Axios
axios.post(`${graphDbEndpoint}?${queryString}`, null, { headers })
  .then(response => {
    var lista = response.data.results.bindings
    var elems=[]
    lista.forEach(element => {
      elems.push({'na' : element.na.value, 'nome' : element.nome.value})
    });
    console.log(elems)
    // Handle the SPARQL query response
    //console.log(response.data.results.bindings);
  })
  .catch(error => {
    // Handle errors
    console.error(error);
  });
