function generarTablaVehiculos(vehiculos) {
  var table = document.createElement('table');
  table.classList.add('vehiculos-table');

  var thead = document.createElement('thead');
  var headerRow = document.createElement('tr');
  var headers = ['Matrícula', 'Tipo de Vehículo', 'Marca', 'Modelo', 'Año'];

  headers.forEach(function (headerText) {
    var th = document.createElement('th');
    th.textContent = headerText;
    headerRow.appendChild(th);
  });

  thead.appendChild(headerRow);
  table.appendChild(thead);

  var tbody = document.createElement('tbody');
  vehiculos.forEach(function (vehiculo) {
    var row = document.createElement('tr');

    var data = [
      vehiculo.matricula,
      vehiculo.tipoVehiculo,
      vehiculo.marca,
      vehiculo.modelo,
      vehiculo.anio.toString()
    ];

    data.forEach(function (text) {
      var cell = document.createElement('td');
      cell.textContent = text;
      row.appendChild(cell);
    });

    tbody.appendChild(row);
  });

  table.appendChild(tbody);

  return table;
}
