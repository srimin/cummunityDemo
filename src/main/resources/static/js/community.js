function commentPost(){
    var postsId = $("#posts_id").val();
    var content = $("#comment_content").val();
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify({
            "parentId":postsId,
            "content":content,
            "type":1
        }),
        success: function (response) {
            if (response.code === 200){
                window.location.reload();
            }else {
                alert(response.message);
            }

        },
        dataType: "json"
    });
}