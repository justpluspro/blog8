'use strict';

let Comment = (function() {

    function Comment(name, id, config) {
        if(config.page === undefined || config.page < 1) {
            config.page = 1;
        }
        if(config.size === undefined || config.size < 5 || config.size > 20) {
            config.size = 5;
        }
        let container = document.getElementById(config.container);
        container.innerHTML = getTabsHtml();

        let data = getComments(name, id, config);

        renderCommentsHtml(data);
        bindClickEvent()
    }


    function getComments(name, id, config) {
        let page = config.page;
        let size = config.size;

        let data = {};

        $.ajax({
            url: '/api/' + name + '/' + id + '/comments?page=' + page + '&size=' + size,
            dataType: 'json',
            contentType: 'application/json;charset=utf8',
            method: 'GET',
            success: function(res) {
                data =  res.data;
            },
            error: function(error) {}
        })
        return data;
    }


    function renderCommentsHtml(data) {
        for(let i = 0; i < data.length; i++) {
            html += '<div class="row">';
            html +=    '<div class="col-auto">';
            html +=        '<span class="avatar avatar-sm avatar-rounded" style="background-image: url(/static/avatars/guest.jpg)"></span>';
            html +=    '</div>';
            html +=    '<div class="col">';
            html +=       '<div class="text-truncate">';
            html +=            '<strong>' + data[i].name +'</strong>';
            html +=       '</div>';
            html +=    '<div>';
            html +=           data[i].content
            html +=    '</div>';
            html +=    '<div>';
            html +=         '<a href="javascript:void(0);" data-reply="'+data[i].id+'">';
            html +=                '<svg xmlns="http://www.w3.org/2000/svg" class="icon" width="24" height="24" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round"> ' +
                '<path stroke="none" d="M0 0h24v24H0z" fill="none"/> ' +
                '<path d="M18 18v-6a3 3 0 0 0 -3 -3h-10l4 -4m0 8l-4 -4"/></svg>';
            html +=         '</a>';
            html +=    '</div>';
            html += '</div>';
            html +=    '<div class="col-auto align-self-center">';
            html +=       '<small class="text-muted">2021-05-11 23:36:06</small>';
            html +=   '</div>';
            html +=  '</div>';
        }
        document.getElementById('profile').innerHTML = html;

        for(elem of document.querySelectorAll('a[data-reply]')) {
            elem.addEventListener('click', function() {
                let parentId = this.dataset.reply;
                console.log('显示' + parentId)
                $('#commentTab a[href="#profile"]').tab('show');
                // $('#tabs-profile-ex2 a[href="#tabs-profile-ex2"]').tab('show');
            })
        }
    }

    Comment.prototype.renderOneCommentHtml = function() {

    }

    function bindClickEvent(config) {

    }

    function getTabsHtml(el) {
        console.log("el" + el);
        let tabsHtml = '<ul class="nav nav-tabs mb-3" id="commentTab" role="tablist">' +
            '          <li class="nav-item" role="tab">' +
            '             <a href="javascript:void(0);" class="nav-link active" data-bs-toggle="tab">评论</a>' +
            '          </li>' +
            '          <li class="nav-item" role="tab">' +
            '             <a href="javascript:void(0);" class="nav-link" data-bs-toggle="tab">评论框</a>' +
            '          </li>' +
            '       </ul>';
        return tabsHtml;
    }

    function loadComments() {

    }

    return Comment;
})();