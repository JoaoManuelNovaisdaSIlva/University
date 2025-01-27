var http = require('http')
var fs = require('fs')
var url = require('url')
var axios = require('axios')

function genOcorrencias(ocorrencias){
    pagHTML = `
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Lista de Ocorrencias</title>
        <link rel="stylesheet" href="w3.css"/>
    </head>
    <body>
        <div class="w3-card-4">
    
            <header class="w3-container w3-blue">
                <h1>Lista de Ocorrencias</h1>
            </header>
            
            <div class="w3-container">
                <table class="w3-table w3-striped">
                    <tr>
                        <th>Identificador</th>
                        <th>Data de ocorrencia</th>
                        <th>Animal</th>
                    </tr>
    `
    ocorrencias.forEach(ocorr => {
        pagHTML += `
        <tr>
            <td><a href='/ocorrencias/${ocorr.id}'> ${ocorr.id}</a></td>
            <td>${ocorr.bite_date}</td>
            <td>${ocorr.SpeciesIDDesc}</td>
        </tr>
        `
    });
    pagHTML += `
            </table>
        </div>
        
        <footer class="w3-container w3-blue">
            <h5>Generated by EngWeb2024 TP1</h5>
        </footer>
        
    </div>
    </body>
    </html>`

    return pagHTML;
}

function genOcorrenciaId(dados){
    return;
}

http.createServer(function (req, res) {
    var regex = /^\/a[0-9]{1,3}$/
    var q = url.parse( req.url, true)
    if(q.pathname == '/'){
        fs.readFile('index.html', function(erro, dados){
            res.writeHead(200, {'Content-Type': 'text/html'})
            res.write(dados)
            res.end()
        }) 
    }
    else if(q.pathname == "/ocorrencias"){
        axios.get('http://localhost:3000/ocorrencias',)
        .then(function(resp){
            dados = resp.data;
            pagHTML = genOcorrencias(dados);
            res.writeHead(200, {'Content-Type': 'text/html'})
            res.write(pagHTML)
            res.end()
        })
        .catch(function(erro){
            res.writeHead(500, {'Content-Type': 'text/html'})
            res.write('<pre>' + erro + '</pre>')
            res.end()
        })
    }
    else if(q.pathname.startsWith('/ocorrencias/')){
        id = q.pathname.split('/')[2];
        axios.get(`http://localhost:3000/ocorrencias/${id}`)
        .then(function(resp){
            dados = resp.data;
            res.write('<pre> DEU </pre>');
            res.end();
            //pagHTML = genOcorrenciaId(dados);
        })
    }
    else if(q.pathname == '/w3.css'){
        fs.readFile('w3.css', function(erro, dados) {
            res.writeHead(200, {'Content-Type': 'text/css'})
            res.write(dados)
            res.end()
        })
    }
    else{
        res.writeHead(400, {'Content-Type': 'text/html; charset=utf-8'})
        res.write('<p>Erro: pedido não suportado.</p>')
        res.write('<pre>' + q.pathname + '</pre>')
        res.end()
    }
    console.log(q.pathname)
}).listen(7777)