function wrapSelection(nombreTag){
    seleccionado = getSelection();
    range = seleccionado.getRangeAt(0);
    marca = document.createElement(nombreTag);
    marca.appendChild(range.extractContents());
    range.insertNode(marca);
    
}
function negrita(){
   // window.document.execCommand("bold",false,null);
    wrapSelection("strong");
}
function cursiva(){
    wrapSelection("em");
}
function subrayado(){
    wrapSelection("u");
}
function subi(){
    wrapSelection("sub");
}
function supi(){
    wrapSelection("sup");
}

//Alineacion

function setAlineacion(alineacion){
    seleccionado = getSelection();
    range = seleccionado.getRangeAt(0);
    contenedor = range.commonAncestorContainer;
    contenedor = contenedor.parentNode;
    contenedor.style.textAlign = alineacion;
}
function alineai(){
    setAlineacion("left");
}
function alinead(){
    setAlineacion("right");
}
function alineac(){
    setAlineacion("center");
}
function alineaj(){
    setAlineacion("justify");
}

//Listas

function listano(){
    seleccionado = getSelection();
    range = seleccionado.getRangeAt(0);
    ul = document.createElement("ul");
    li = document.createElement("li");

    li.appendChild(range.extractContents());
    ul.appendChild(li);

    range.insertNode(ul);
}

function listao(){
    seleccionado = getSelection();
    range = seleccionado.getRangeAt(0);
    ul = document.createElement("ol");
    li = document.createElement("li");

    li.appendChild(range.extractContents());
    ul.appendChild(li);

    range.insertNode(ul);
}

//Color de texto y fondo

function fontColor(){
    color = prompt("Introduce el color de la fuente");
    seleccionado = getSelection();
    range = seleccionado.getRangeAt(0);
    span = document.createElement("span");
    span.style.color = color;
    span.appendChild(range.extractContents());
    range.insertNode(span);
}
function backgroundColor(){
    color = prompt("Introduce el color de la fuente");
    seleccionado = getSelection();
    range = seleccionado.getRangeAt(0);
    span = document.createElement("span");
    span.style.backgroundColor = color;
    span.appendChild(range.extractContents());
    range.insertNode(span);
}
