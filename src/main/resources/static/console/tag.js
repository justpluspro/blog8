let rootPath = '/';
$(document).ready(function () {
    let queryObject = {};
    queryObject.page = 1;
    queryObject.query = "";
    queryTags(queryObject);
});

document.getElementById('add-tag-btn').addEventListener('click', function () {
    (async () => {
        const {value: name} = await Swal.fire({
            title: '标签名称',
            input: 'text',
            inputLabel: '',
            inputPlaceholder: '名称',
            confirmButtonText: '确定'
        })

        if (name) {
            $.ajax({
                url: rootPath + 'api/tag',
                method: 'POST',
                contentType: 'application/json;charset=utf8',
                dataType: 'json',
                data: JSON.stringify({'name': name}),
                success: function (res) {
                    let queryObject = {};
                    queryObject.page = 1;
                    queryObject.query = "";
                    queryTags(queryObject);
                }
            });
        }
    })()
});


function queryTags(queryObject) {
    let html = "";
    $.ajax({
        url: rootPath + 'api/tags?page=' + queryObject.page + '&query=' + queryObject.query,
        dataType: 'json',
        success: function (res) {
            let tags = res.data;
            if (tags.length > 0) {
                for (let i = 0; i < tags.length; i++) {
                    let tag = tags[i];
                    html += '<tr>';
                    html += '<td>' + tag.name + '</td>';
                    html += '<td>' + tag.createAt + '</td>';
                    html += '<td>' +
                        '<a href="javascript:editTag(\'' + tag.id + '\', \'' + tag.name + '\');">' +
                        '<svg xmlns="http://www.w3.org/2000/svg" class="icon" width="24" height="24" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round"> <path stroke="none" d="M0 0h24v24H0z" fill="none"/> <path d="M9 7h-3a2 2 0 0 0 -2 2v9a2 2 0 0 0 2 2h9a2 2 0 0 0 2 -2v-3"/> <path d="M9 15h3l8.5 -8.5a1.5 1.5 0 0 0 -3 -3l-8.5 8.5v3"/> <line x1="16" y1="5" x2="19" y2="8"/> </svg>' +
                        '</a>' +
                        '<a class="text-danger" href="javascript:deleteTag(\'' + tag.id + '\');">' +
                        '<svg xmlns="http://www.w3.org/2000/svg" class="icon" width="24" height="24" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><line x1="4" y1="7" x2="20" y2="7" /><line x1="10" y1="11" x2="10" y2="17" /><line x1="14" y1="11" x2="14" y2="17" /><path d="M5 7l1 12a2 2 0 0 0 2 2h8a2 2 0 0 0 2 -2l1 -12" /><path d="M9 7v-3a1 1 0 0 1 1 -1h4a1 1 0 0 1 1 1v3" /></svg>' +
                        '</a>' +
                        '</td>'
                    html += '</tr>';
                }
            } else {
                html += '<tr><td style="text-align: center" colspan="3">无数据</td></tr>';
            }
            document.getElementById('tagContainer').innerHTML = html;
            loadPagination(res);
        }
    });
}

function loadPagination(pageDto) {
    let pagination = "";
    let start;
    let end;

    //总页码超过8页
    if (pageDto.totalPage < 8) {
        start = 1;
        end = pageDto.totalPage;
    } else {
        //总页码超过8页
        start = pageDto.page - 4;
        end = pageDto.page + 3;

        //如果前面不够4个
        if (start < 1) {
            start = 1;
            end = start + 7;
        }

        //如果后面不足3个，前面补齐8个
        if (end > pageDto.totalPage) {
            end = pageDto.totalPage;
            start = end - 7;
        }
    }
    for (let i = start; i <= end; i++) {
        if (pageDto.page === i) {
            pagination += '<li class="page-item active"><a class="page-link" href="javascript:toPage(' + i + ')">' + i + '</a></li>';
        } else {
            pagination += '<li class="page-item"><a class="page-link" href="javascript:toPage(' + i + ')">' + i + '</a></li>';
        }
    }
    document.getElementById('page-container').innerHTML = pagination;
}

function toPage(page) {
    let queryObject = {
        "page": page,
        "size": 10,
        "query": document.getElementById('query').value
    }
    queryTags(queryObject);
}

function deleteTag(id) {
    Swal.fire({
        title: '您确定吗?',
        text: '确定要移除该标签吗?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '确定',
        cancelButtonText: '取消'
    }).then((result) => {
        if (result.value) {
            $.ajax({
                url: rootPath + 'api/tag/' + id,
                method: 'DELETE',
                dataType: 'json',
                success: function () {
                    let queryObject = {};
                    queryObject.page = 1;
                    queryObject.query = "";
                    queryTags(queryObject);
                }
            })
        }
    });
}

function editTag(id, name) {
    (async () => {
        const {value: name} = await Swal.fire({
            title: '标签名称',
            input: 'text',
            inputLabel: '',
            inputValue: name,
            inputPlaceholder: '名称',
            confirmButtonText: '确定'
        })

        if (name) {
            $.ajax({
                url: rootPath + 'api/tag/' + id,
                method: 'PUT',
                contentType: 'application/json;charset=utf8',
                dataType: 'json',
                data: JSON.stringify({'name': name}),
                success: function (res) {
                    let queryObject = {};
                    queryObject.page = 1;
                    queryObject.query = "";
                    queryTags(queryObject);
                }
            });
        }
    })()
}


$('#query').bind('keypress', function (event) {
    if (event.keyCode === 13) {
        search();
    }
});

document.getElementById('search-tag-btn').addEventListener('click', function () {
    search();
});

function search() {
    let Query = {};
    let value = document.getElementById('query').value;
    Query.page = 1;
    Query.size = 10;
    Query.query = value;

    queryTags(Query);
}

