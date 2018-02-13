function loadDocs() {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      var response = JSON.parse(this.responseText);

      var table = document.createElement("TABLE");
      var header = table.createTHead();
      var row = header.insertRow(0);
      row.insertCell().innerHTML="<b>Order</b>";
      row.insertCell().innerHTML="<b>Folder</b>";
      row.insertCell().innerHTML="<b>From</b>";
      row.insertCell().innerHTML="<b>Subject</b>";
      row.insertCell().innerHTML="<b>Sent date</b>";
      row.insertCell().innerHTML="<b>Received date</b>";
      row.insertCell().innerHTML="<b>Recipients</b>";
      row.insertCell().innerHTML="<b>Reply to</b>";

      var hit;
      for (i = 0; i < response.hits.hits.length; i++) {
        hit = response.hits.hits[i];

        var row = table.insertRow();
        var numberCell = row.insertCell();
        var folderCell = row.insertCell();
        var fromCell = row.insertCell();
        var subjectCell = row.insertCell();
        var sentDateCell = row.insertCell();
        var receivedDateCell = row.insertCell();
        var recipientsCell = row.insertCell();
        var replyToCell = row.insertCell();

        numberCell.innerHTML=i;

        folderCell.innerHTML=hit._source.Folder;
        fromCell.innerHTML=escapeHtml(hit._source.From.toString());
        subjectCell.innerHTML=hit._source.Subject;
        sentDateCell.innerHTML=hit._source["Sent date"];
        receivedDateCell.innerHTML=hit._source["Received Date"];
        recipientsCell.innerHTML=escapeHtml(hit._source.Recipients.toString());
        replyToCell.innerHTML=escapeHtml(hit._source["Reply to"].toString());
      }

      document.getElementById("results").innerHTML="";
      document.getElementById("results").append(table);
    }
  };
  function escapeHtml(unsafe) {
    return unsafe
         .replace(/&/g, "&amp;")
         .replace(/</g, "&lt;")
         .replace(/>/g, "&gt;")
         .replace(/"/g, "&quot;")
         .replace(/'/g, "&#039;");
  };
  xhttp.open("POST", document.getElementById("url").value, true);
  xhttp.setRequestHeader("Content-Type", "application/json");
  xhttp.send(document.getElementById("query").value);
}