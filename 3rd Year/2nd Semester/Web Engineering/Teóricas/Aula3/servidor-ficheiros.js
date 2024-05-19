var http = require('http');
var fs = require('fs');
var url = require('url');

http.createServer(function (req, res) {
    var regex = /^\/[123]$/
    var q = url.parse(req.url, true);
    if(regex.test(q.pathname)){
        fs.readFile('pag' + q.pathname.substring(1) + ".html", function(erro, dados){
            res.writeHead(200, {'Content-Type': 'text/html; charset=utf-8'});
            res.write(dados);
            res.end();
        })
    }else if(q.pathname == '/we.css'){
        fs.readFile('w3.css', function(erro, dados){
            res.writeHead(200, {'Content-Type': 'text/css'})
            res.write(dados);
            res.end();
        })
    }else{
        res.writeHead(400, {'Content-Type': 'text/html; charset=utf-8'});
        res.write('<p> Erro: pedido não suportado </p>');
        res.end();
    }
}).listen(7777);
