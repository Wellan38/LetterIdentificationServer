var mousePressed = false;
var lastX, lastY;
var ctx;
var thickness = '6';

(function()
{
	InitThis();
}());

function InitThis() {
    ctx = document.getElementById('myCanvas').getContext("2d");

    $('#myCanvas').mousedown(function (e) {
        mousePressed = true;
        Draw(e.pageX - $(this).offset().left, e.pageY - $(this).offset().top, false);
    });

    $('#myCanvas').mousemove(function (e) {
        if (mousePressed) {
            Draw(e.pageX - $(this).offset().left, e.pageY - $(this).offset().top, true);
        }
    });

    $('#myCanvas').mouseup(function (e) {
        mousePressed = false;
    });
	    $('#myCanvas').mouseleave(function (e) {
        mousePressed = false;
    });
}

function Draw(x, y, isDown) {
    if (isDown) {
        ctx.beginPath();
        ctx.strokeStyle = 'black';
        ctx.lineWidth = thickness;
        ctx.lineJoin = "round";
        ctx.moveTo(lastX, lastY);
        ctx.lineTo(x, y);
        ctx.closePath();
        ctx.stroke();
    }
    lastX = x; lastY = y;
}
	
function clearArea() {
    // Use the identity matrix while clearing the canvas
    ctx.setTransform(1, 0, 0, 1, 0, 0);
    ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);
}

function save()
{
    var letter = $('#letter option:selected').text();
    
    $.ajax({
        url: './ActionServlet',
        type: 'POST',
        data: {
            action: 'upload',
            image: document.getElementById("myCanvas").toDataURL("image/png").split(",")[1],
            letter: letter
        },
        async:false,
        dataType: 'json'
    })
    .done(function(data) {
              
    })
    .fail(function() {
        console.log('Upload error');
    })
    .always(function() {
        //
    });
    
    clearArea();
}

function test()
{    
    $.ajax({
        url: './ActionServlet',
        type: 'POST',
        data: {
            action: 'test',
            image: document.getElementById("myCanvas").toDataURL("image/png").split(",")[1],
        },
        async:false,
        dataType: 'json'
    })
    .done(function(data) {
              
    })
    .fail(function() {
        console.log('Upload error');
    })
    .always(function() {
        //
    });
}