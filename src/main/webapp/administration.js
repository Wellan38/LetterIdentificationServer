/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var images = [];

(function()
{
    init();
}());

function init()
{
    listImages();
}

function listImages()
{
    $.ajax({
        url: './ActionServlet',
        type: 'GET',
        data: {
            action: 'list',
            type: "images_to_be_analyzed"
        },
        async:false,
        dataType: 'json'
    })
    .done(function(data) {
        images = data.list;
        var html = "";

        for (var i = 0; i < images.length; i++)
        {
            html += "<div class='panel panel-default'><div class='panel-heading'>" + images[i].letter + "</div>";
            html += "<div class='panel-body' style='padding: 15px 0px 0px 0px;'>";
            
            for (var j = 0; j < images[i].drawings.length; j++)
            {
                html += "<div class='col-md-6'>";
                html += "<div class='well well-sm' style='padding: 15px 0px 15px 15px; margin-bottom: 15px;'><div class='row' style='display: table; width: 100%'>";
                html += "<div class='col-xs-11' style='display: table-cell; float: none;'><div class='col-xs-6' style='padding: 0px 7px 0px 0px;'><img src='" + images[i].drawings[j] + "' style='border: 2px solid black;'/></div>";
                html += "<div class='col-xs-6' style='padding: 0px 0px 0px 7px;'><img src='" + images[i].computer_vision[j] + "' style='border: 2px solid black;'></div></div>";
                html += "<div class='col-xs-1' style='vertical-align: middle; display: table-cell; float: none; text-align: center;'><div class='row' style='margin-bottom: 10px;'><i class='clickable fa fa-thumbs-o-up fa-2x'></i></div>";
                html += "<div class='row' style='margin-top: 10px;'><i class='clickable fa fa-thumbs-o-down fa-2x''></i></div></div>";
                html += "</div></div></div>";
            }
            
            html += "</div></div>";
        }
        
        $('#list_images').html(html);
    })
    .fail(function() {
        console.log("Couldn't load the list.");
    })
    .always(function() {
        //
    });
}