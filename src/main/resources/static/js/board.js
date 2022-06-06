let index = {
    init : function(){
        $("#btn-save").on("click", ()=>{
            this.save();
        });

        $("#btn-login").on("click", ()=>{
            this.login();
        });
    },

    save: function (){
        let data = {
            title: $("#title").val(),
            content: $("#content").val(),
        };

        console.log(data);

        // ajax호출시 default가 비동기 호출
        $.ajax({
            //글쓰기 수행요청
            type: "POST",
            url: "/api/board",
            data: JSON.stringify(data), //http body데이터
            contentType: "application/json; charset=utf-8",  //body데이터가 어떤 타입인지(MIME)
            dataType: "json"  // 요청을 서버로해서 응답이 왔을때 기본적으로 모든것이 String(문자열)
                              // 생긴게 json이라면 -> javascript로 변환
        }).done(function(resp){
            alert("글쓰기가 완료되었습니다.");
            location.href = "/board/saveForm";
        }).fail(function(){
            alert(JSON.stringify(onerror));
        }); //ajax통신을 이용해서 3개의 데이터를 JSON으로 변경하여 Insert요청!!
     },
}

index.init();