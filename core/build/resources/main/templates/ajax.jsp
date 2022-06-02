<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
	function ajaxTest(){
		$.ajax({
			url: "ajax",
			type: "GET",
			success : function(){alert("통신성공")},
			error : function(){alert("통신실패")}
		})
	}
</script>
</head>
<body>
<h1>일</h1>	<h1>일</h1>
	<h1>일</h1>	<h1>일</h1>
	<h1>일</h1>	<h1>일</h1>
	<h1>일</h1>	<h1>일</h1>
	<button type="button" onclick="ajaxTest()">click</button>
</body>