// Function to load JSON data using jQuery
function loadJSON(callback) {
    $.getJSON('metaENGWEB2024.json', function(data) {
      callback(data);
    });
  }

  // Function to inject JSON data into HTML using jQuery
  function injectData(data) {
    $('#sigla').html(data.sigla);
    $('#titulo').html(data.titulo);
    $('#teste').html('Teste: ' + data.datas.teste);
    $('#exame').html('Exame: ' + data.datas.exame);
    $('#projeto').html('Projeto: ' + data.datas.projeto);

    docentesContent = ""
    data.docentes.forEach(function (element){
        docentesContent += `<div>
        <div class="w3-container w3-margin\">
            <img src="${element.foto}" alt="Foto pessoal" class="w3-grayscale-min" style="width:20%">
          </div>
          <p><i class="fas fa-user-tie fa-fw w3-margin-right w3-large w3-text-teal"></i>${element.nome}</p>
          <p><i class="fa fa-briefcase fa-fw w3-margin-right w3-large w3-text-teal"></i>${element.categoria}</p>
          <p><i class="fa fa-home fa-fw w3-margin-right w3-large w3-text-teal"></i>${element.filiacao}</p>
          <p><i class="fa fa-envelope fa-fw w3-margin-right w3-large w3-text-teal"></i>${element.email}</p>
          <p><i class="fas fa-paw fa-fw w3-margin-right w3-large w3-text-teal"></i><a href="${element.webpage}">Outras informações</a></p>
        </div>  
          <hr></hr>`
    })
    $('#docentes').html(docentesContent);

    teoricasContent = ""
    data.horario.teoricas.forEach(function (element){
        teoricasContent += "<li>" + element + "</li>\n"
    });
    $('#teoricas').html(teoricasContent);

    praticasContent = ""
    data.horario.praticas.forEach(function (element){
        praticasContent += "<li>" + element + "</li>\n"
    });
    $('#praticas').html(praticasContent);

    avaliacaoContent = ""
    data.avaliacao.forEach(function (element){
        avaliacaoContent += "<li>" + element + "</li>\n"
    });
    $('#avaliacao').html(avaliacaoContent);

    // ------- Geração do índice cronológico de aulas ---------------------
    var aulas = data.aulas;
    aulas.sort(function (a, b) {
      return a.data.localeCompare(b.data);
    })
    indiceContent = ""
    aulaTeorica = 1
    aulaPratica = 1
    aulas.forEach(function(aula){
      if(aula.tipo == 'T'){
        indiceContent += `<div class="w3-bar-item"><a href="#${aula.tipo}${aulaTeorica}">${aula.tipo}${aulaTeorica}</a></div>`
        aulaTeorica++;
      }
      else{
        indiceContent += `<div class="w3-bar-item"><a href="#${aula.tipo}${aulaPratica}">${aula.tipo}${aulaPratica}</a></div>`
        aulaPratica++;
      }  
    })
    $('#indice_aulas').html(indiceContent);


    // -----------------------------------------------------------------------
    // ------- Geração do conteúdo das aulas
    aulasContent = ""
    aulaTeorica = 1
    aulaPratica = 1
    const markdownLinkRegex = /\[([^\]]+)\]\(([^)]+)\)/g;
    aulas.forEach(function(aula){
      if(aula.tipo == 'T'){
        aulasContent += `<div class="w3-container" id="${aula.tipo}${aulaTeorica}">
        <h6 class="w3-text-teal"><i class="fa fa-calendar fa-fw w3-margin-right"></i>Aula Teórica ${aulaTeorica}: ${aula.data}</h6>`
        aulaTeorica++;
      }
      else{
        aulasContent += `<div class="w3-container" id="${aula.tipo}${aulaPratica}">
        <h6 class="w3-text-teal"><i class="fa fa-calendar fa-fw w3-margin-right"></i>Aula Prática ${aulaPratica}: ${aula.data}</h6>`
        aulaPratica++;
      }  
      aula.sumario.forEach(function(para){
        // Convert Markdown links to HTML links
        const hpara = para.replace(markdownLinkRegex, '<a href="$2">$1</a>');
        aulasContent += `<p>${hpara}</p>`
      })
      aulasContent += `<address>[<a href="#indice_aulas">Voltar ao índice]</a></address>`
      aulasContent += "</div><hr/>"
    })
    $('#aulas').html(aulasContent);
    // -------------------------------------------------------------------------
  }
  

  // Load JSON data and inject into HTML when the page loads
  $(document).ready(function() {
    loadJSON(function(data) {
      injectData(data);
    });
  });