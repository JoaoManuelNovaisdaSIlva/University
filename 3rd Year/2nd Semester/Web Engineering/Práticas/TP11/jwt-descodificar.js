var jwt = require('jsonwebtoken');

var token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImpjciIsImxldmVsIjoiZWRpdG9yIiwiaWF0IjoxNzE1MDA1MDU5LCJleHAiOjE3MTUwMDUxNTl9.uYfmZOp5w8Hybb0I_4Z14IeZYisaT-FuUe4-uDOTzOo"
jwt.verify(token, 'EngWeb2024', function(e, payload){
    if(e) console.log('Erro na verificaÃ§Ã£o do token: ' + e)
    else console.log('Payload: ' + JSON.stringify(payload))
})