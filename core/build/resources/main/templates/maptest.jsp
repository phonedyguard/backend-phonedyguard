<script
        src="https://apis.openapi.sk.com/tmap/jsv2?version=1&appKey=l7xx491b556af3b746c28a219e091337e03f"></script>
<script type="text/javascript">
var map;
var marker_s, marker_e, marker_p1, marker_p2;
var totalMarkerArr = [];
var drawInfoArr = [];
var resultdrawArr = [];
var lat;
var lng;
            .ajax({
                method : "POST",
                url : "ajax2",
                async : false,
                data : {
                    "appKey" : "l7xx491b556af3b746c28a219e091337e03f",
                    "startX" : "128.958371",
                    "startY" : "35.111012",
                    "endX" : "128.959404",
                    "endY" : "35.114454",
                    "reqCoordType" : "WGS84GEO",
                    "resCoordType" : "EPSG3857",
                    "startName" : "출발지",
                    "endName" : "도착지"
                },
                success : function(data) {
                    var resultData = data.features;

                    var tDistance = "총 거리 : "
                            + ((resultData[0].properties.totalDistance) / 1000)
                                    .toFixed(1) + "km,";
                    var tTime = " 총 시간 : "
                                    .toFixed(0) + "분";
                            + ((resultData[0].properties.totalTime) / 60)

                    $("#result").text(tDistance + tTime);

                    drawInfoArr = [];

                    for ( var i in resultData) { //for문 [S]
                        var geometry = resultData[i].geometry;
                        var properties = resultData[i].properties;
                        var polyline_;


                        if (geometry.type == "LineString") {
                            for ( var j in geometry.coordinates) {
                                // 경로들의 결과값 DB에 저장
                                    lat = geometry.coordinates[j][0]; //위도
                                    lng = geometry.coordinates[j][1]; //경도
                            }
                        }
                    }
                    drawLine(drawInfoArr);
                },
                error : function(request, status, error) {
                    console.log("code:" + request.status + "\n"
                            + "message:" + request.responseText + "\n"
                            + "error:" + error);
                }
            });

function addComma(num) {
    var regexp = /\B(?=(\d{3})+(?!\d))/g;
    return num.toString().replace(regexp, ',');
}
