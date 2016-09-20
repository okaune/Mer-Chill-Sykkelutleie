$(document).ready(function(){
   $(".selectpicker").change(function() {
       var id = $(this).val();
       $.ajax({
           url: "rest/parkingspots/" + id,
           type: 'GET',
           data: {
               format: 'json'
           },
           success: function(data) {
               var bikes =  $(".available-bikes");
               bikes.html("");
               for (var bike in data.availableBikes) {
                   bikes.append(
                       '<div class="bike">' +
                            '<div class="bike__icon">' +
                                '<i class="fa fa-bicycle" aria-hidden="true"></i>' +
                            '</div>' +
                            '<div class="bike__info">' +
                                '<h3>' + data.availableBikes[bike].id + '</h3>' +
                                '<div class="progress" aria-valuenow="' + (data.availableBikes[bike].batteryPercentage * 100) + '">' +
                                    '<div class="progress-bar progress-bar-' + (((data.availableBikes[bike].batteryPercentage * 100) > 50) ? 'success' : 'danger') + '" aria-valuenow="' + (data.availableBikes[bike].batteryPercentage * 100) + '" aria-valuemin="0" aria-valuemax="100" style="width:' + (data.availableBikes[bike].batteryPercentage * 100) + '%"></div>' +
                                '</div>' +
                            '</div>' +
                       '</div>'
                   );
               }
           }
       });
   });
});
