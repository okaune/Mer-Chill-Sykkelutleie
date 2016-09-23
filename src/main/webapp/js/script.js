$(document).ready(function(){
    $.ajax({
        url: "rest/parkingspots/",
        type: 'GET',
        data: {
            format: 'json'
        },
        success: buildDropdown
    });

   $(".selectpicker").change(function() {
       var id = $(this).val();
       var mainArea = $(".main-area");
       var reserveBtn = $(".btn-reserve");
       reserveBtn.prop("disabled", false);
       mainArea.addClass("selected");
       $.ajax({
           url: "rest/parkingspots/" + id,
           type: 'GET',
           data: {
               format: 'json'
           },
           success: addContent
       });
   });

});

$(document).on("click", '.bike', function(e) {
    openBikeModal(e);
});

$(document).on('keypress', '.bike', function(e) {
    if(e.keyCode == 13) {
        openBikeModal(e);
    }
});

function openBikeModal(event) {
    var id = event.currentTarget.innerText.replace(/(\r\n|\n|\r)/gm,"");

    $("body").append(
        '<div class="popup reserveModal">' +
        '<h3>Vil du reservere sykkel nr. ' + id + '?</h3>' +
        '<div class="btn-group">' +
        '<button class="btn btn-default btn-confirm" data-id="' + id + '">Ja</button>' +
        '<button class="btn btn-default btn-cancel">Nei</button>' +
        '</div>' +
        '</div>'
    );
}

$(document).on('click', '.dummy', function(e) {
    openDummyModal(e);
});

function openDummyModal(event) {
    $("body").append(
        '<div class="popup reserveModal">' +
        '<h3>Ehh...</h3>' +
        '<p>Tror du virkelig vi har rukket å legge inn noe nyttig info her?</p>' +
        '<div class="btn-group">' +
        '<button class="btn btn-default btn-cancel">Lukk</button>' +
        '</div>' +
        '</div>'
    );
}

$(document).on('click', '.reserveModal .btn-confirm', function(e) {
    console.log(e);
    openReserveModal(e.currentTarget.dataset.id);
});

$(document).on('click', '.btn-reserve', function(e) {
    openReserveModal(12);
});

function openReserveModal(id) {
    var currentDate = new Date();
    currentDate.setTime(currentDate.getTime() + 30*60*1000);

    $("body").append(
        '<div class="popup reserveModal">' +
        '<h3>Reservert</h3>' +
        '<p>Du har reservert sykkelnr. ' + id + '</p>' +
        '<p>Koden ved uthenting ' + Math.floor(id * 100 * Math.random()) + '</p>' +
        '<p>Sykkelen må hentes innen ' + ('0' + currentDate.getHours()).slice(-2) + ':' + ('0' + currentDate.getMinutes()).slice(-2) + '</p>' +
        '<div class="btn-group">' +
        '<button class="btn btn-default btn-cancel">Lukk</button>' +
        '</div>' +
        '</div>'
    );
}

$(document).on("click", '.btn-cancel', function() {
    var popup = $(".popup");
    popup.animate(
        {
            top: 0,
            opacity: 0
        },
        300,
        "swing",
        function() {
            $(this).remove();
        }
    );
});

function buildDropdown(data) {
    var dropdown = $(".selectpicker");
    for (var location in data) {
        dropdown.append(
            '<option value="' + data[location].id + '">' + data[location].locationName + '</option>'
        );
    }
}

function addContent(data) {
    var bikes =  $(".available-bikes");
    var parkingInfo = $(".parking-info");

    console.log(data);

    parkingInfo.html(
        '<p>Sykler tilgjengelig: <span>' +
        data.availableBikes.length +
        '</span> Ledige plasser: <span>' +
        (data.capacity - data.availableBikes.length) +
        '</span></p>'
    );
    bikes.html("");
    for (var bike in data.availableBikes) {
        bikes.append(
            '<div class="bike" tabindex="0">' +
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