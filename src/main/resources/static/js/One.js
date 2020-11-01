// 问题点赞功能
function collapseQuestionLike(e) {
    let id = e.getAttribute("data-question-like");
    console.log(id);
    likeQuestionCount(id);
}

function likeQuestionCount(id) {
    $.ajax({
        type: "POST",
        url: "/question/likeCount/" + id,
        contentType: 'application/json',
        data: JSON.stringify({
            "id": id
        }),
        success: function (response) {
            if (response.code === 200) {
                window.location.reload();
            } else {
                if (response.code === 200) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("http://github.com/login/oauth/authorize?client_id=b3b546e022ca3539a8bb&redirect_url=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}


// 评论实现点赞功能
function collapseCommentLike(e) {
    let id = e.getAttribute("data-comment-like");
    console.log(id);
    likeCommentCount(id);
}

function likeCommentCount(id) {
    $.ajax({
        type: "POST",
        url: "/comment/likeCount/" + id,
        contentType: 'application/json',
        data: JSON.stringify({
            "id": id
        }),
        success: function (response) {
            if (response.code === 200) {
                window.location.reload();
            } else {
                if (response.code === 200) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("http://github.com/login/oauth/authorize?client_id=b3b546e022ca3539a8bb&redirect_url=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}

//提交回复
function Post() {
    var questionId = $('#input_id').val();
    var commentTxt = $('#comment_id').val();
    commentToTarget(questionId, 1, commentTxt);
}

function commentToTarget(targetId, type, content) {
    if (!content) {
        alert("回复内容为空！！！！");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code === 200) {
                window.location.reload();
                // $("#comment_section").hide();
            } else {
                if (response.code === 200) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("http://github.com/login/oauth/authorize?client_id=b3b546e022ca3539a8bb&redirect_url=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(response.message);
                }

            }
        },
        dataType: "json"
    });

}

function comment(e) {
    var id = e.getAttribute("data-id");
    var content = $('#input-' + id).val();
    commentToTarget(id, 2, content);
}


//取消评论
function close(e) {
    let id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);
    comments.removeClass("in");
}


//显示二级回复


function collapseComments(e) {
    let id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);
    // // comments.toggleClass("in");
    // if (comments.hasClass("in")){
    //     comments.removeClass("in");
    // }else{
    //     $.getJSON("/comment/"+id,function (data) {
    //         console.log(data);
    //         comments.addClass("in");
    //     });
    // }

    //获取一下二级评论展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {

        let commentBody = $("#comment-" + id);
        if (commentBody.children().length !== 1) {
            //展开二级评论
            comments.addClass("in");
            //标记二级评论状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                // console.log(data);
                // console.log(data.listdata);
                $.each(data.listdata.reverse(), function (index, comments) {

                    let timeElement = $("<span/>", {
                        "style": "position: relative;left: 92%;",
                        "text": moment(comments.gmtCreate).format('YYYY-MM-DD')

                    });
                    let hrElement = $("<hr/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comment-sp"
                    });

                    let contentElement = $("<div/>", {
                        "text": comments.content
                    });
                    let H5Element = $("<h5/>", {
                        "style": "color: #2aabd2",
                        "text": comments.user.name
                    });
                    let mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    });

                    let avatarElement = $("<img/>", {
                        "class": "media-object img-circle",
                        "src": comments.user.avatarUrl
                    });

                    let mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    });

                    let mediaElement = $("<div/>", {
                        "class": "media"
                    });


                    let commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
                    });

                    commentBody.prepend(commentElement);
                    commentElement.append(mediaElement);
                    mediaElement.append(mediaLeftElement);
                    mediaLeftElement.append(avatarElement);
                    mediaElement.append(mediaBodyElement);
                    mediaElement.append(hrElement);
                    mediaBodyElement.append(H5Element);
                    mediaBodyElement.append(contentElement);
                    mediaBodyElement.append(timeElement);
                });
            });
        }

        //展开二级评论
        comments.addClass("in");
        //标记二级评论状态
        e.setAttribute("data-collapse", "in");
        e.classList.add("active");

    }
}

function selectTag(e) {
    let previous = $('#tag').val();
    let value = e.getAttribute("data-tag");

    if (previous.indexOf(value) === -1) {
        if (previous) {
            $("#tag").val(previous + " " + value);
        } else {
            $("#tag").val(value);
        }
    }
}

function inputTag() {
    $("#select-tags").show();
}