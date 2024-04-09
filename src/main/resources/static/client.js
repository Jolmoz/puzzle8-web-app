// URL del backend
const url = 'http://localhost:8080/api/solve';

/**
 * Esta funcion obtiene los valores de los input del HTML
 */
function obtenerValores() {
    var values = []
    values.push(document.getElementById("input1").value)
    values.push(document.getElementById("input2").value)
    values.push(document.getElementById("input3").value)
    values.push(document.getElementById("input4").value)
    values.push(document.getElementById("input5").value)
    values.push(document.getElementById("input6").value)
    values.push(document.getElementById("input7").value)
    values.push(document.getElementById("input8").value)
    values.push(document.getElementById("input9").value)

    console.log("Valor del input:", values);
    solve(values)
}

/**
 * Esta función transforma las acciones del backend a cadenas de texto mas entendibles
 * @param {*} action 
 * @returns 
 */
function translateAction(action) {
    switch (action) {
        case "Action[name==Right]":
            return "Derecha";
            break;

        case "Action[name==Left]":
            return "Izquierda";
            break;

        case "Action[name==Up]":
            return "Arriba";
            break;

        case "Action[name==Down]":
            return "Abajo";
            break;

        default:
            return action
            break;
    }
}

// Función para dibujar las matrices recibidas en la página
async function dibujarMatrices(matrices, actions) {
    // Obtener el contenedor donde se agregarán las matrices
    var container = document.getElementById("matrices-container");
    // Limpiar el contenedor antes de agregar nuevas matrices
    container.innerHTML = "";

    // Recorrer cada matriz recibida
    matrices.forEach((matrix, index) => {
        // Crear un contenedor para la matriz
        var matrixWrapper = document.createElement("div");
        matrixWrapper.classList.add("matrix-wrapper");

        // Crear un subtítulo para la matriz
        var subtitle = document.createElement("h2");
        subtitle.textContent = "Paso " + (index + 1) + ", " + translateAction(actions[index]);
        matrixWrapper.appendChild(subtitle)

        // Crear un contenedor para la matriz
        var matrixContainer = document.createElement("div");
        matrixContainer.classList.add("matrix-container");

        // Crear una fila para cada fila de la matriz
        for (var i = 0; i < 3; i++) {
            var row = document.createElement("div");
            row.classList.add("matrix-row");

            // Crear una celda para cada elemento de la fila
            for (var j = 0; j < 3; j++) {
                var cell = document.createElement("div");
                cell.classList.add("matrix-cell");
                // Colocar el valor en la celda
                cell.textContent = matrix[i * 3 + j];
                row.appendChild(cell);
            }
            matrixContainer.appendChild(row);
        }

        // Agregar la matriz al contenedor
        matrixWrapper.appendChild(matrixContainer);
        container.appendChild(matrixWrapper);

        // Agregar una flecha entre matrices, excepto después de la última matriz
        if (index < matrices.length - 1) {
            var arrow = document.createElement("div");
            arrow.classList.add("arrow");
            container.appendChild(arrow);
        }
    });
}


/**
 * Esta función por medio de un consumo al web service del backend envia la solicitud para resolver el puzzle
 * @param {*} values 
 * @returns 
 */
async function solve(values) {

    // Mostrar el overlay oscuro y el spinner
    document.getElementById("overlay").style.display = "block";
    document.querySelector(".spinner-container").style.display = "block";

    // Configurar la solicitud POST
    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(values)
    };

    try {
        const response = await fetch(url, requestOptions);
        if (!response.ok) {
            throw new Error('Error al enviar los datos');
        }
        const data = await response.json();
        console.log('Datos enviados correctamente:', data);
        if (data.states == undefined) {
            document.getElementById("overlay").style.display = "none";
            document.querySelector(".spinner-container").style.display = "none";
            alert(data.msg)
            return
        }

        if (data.states.length == 0) {
            document.getElementById("overlay").style.display = "none";
            document.querySelector(".spinner-container").style.display = "none";
            alert('No se encontró solución')
            return
        }

        if (data.states.length > 0) {
            await dibujarMatrices(data.states, data.actions);
        }

        document.getElementById("overlay").style.display = "none";
        document.querySelector(".spinner-container").style.display = "none";

    } catch (error) {
        console.error('Hubo un error:', error);
        return;
    }

}
