function pageScroll() {
    // window.scrollBy(0,50);
    // scrollTo(0, 40)

    // El scrollTO hace que la pagina inicie siempre en la parte superior, mas abajo del primer div ('barra_principal') -> (x:0, y:div.altura)
    scrollTo(0, document.getElementById('barra_principal').clientHeight);

    // Esto deberia ser implementado o eliminado, porque ya <body> llama está funcion sin necesidad del javascript
}