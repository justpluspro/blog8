let rootPath = '/';
$(document).ready(function() {
    loadCategories();
});

document.getElementById('addCategoryBtn').addEventListener('click', function(){
    (async () => {
        const { value: name } = await Swal.fire({
            title: '分类名称',
            input: 'text',
            inputLabel: '',
            inputPlaceholder: '分类名称',
            confirmButtonText: '确定'
        });

        if (name) {
            $.ajax({
                url: rootPath + 'api/category',
                method: 'post',
                contentType: 'application/json;charset=utf8',
                dataType: 'json',
                data: JSON.stringify({'name': name}),
                success: function (res) {
                    loadCategories();
                }
            });
        }
    })()
});


function loadCategories() {
    let categoryContainer = document.getElementById('categoryContainer');
    let html = "";
    $.ajax({
        url: rootPath + 'api/categories',
        dataType: 'json',
        contentType: 'application/json;charset=utf8',
        method: 'GET',
        success: function(res) {
            if(res.length > 0) {
                for(let i = 0; i < res.length; i++) {
                    let category = res[i];
                    html += '<tr>';
                    html += '<td>'+ category.name +'</td>';
                    html += '<td>'+ category.createAt +'</td>';
                    html += '<td>' +
                        '<a href="javascript:void(0);" data-name="'+category.name+'" data-edit="'+category.id+'">' +
                        '<svg xmlns="http://www.w3.org/2000/svg" class="icon" width="24" height="24" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M12 15l8.385 -8.415a2.1 2.1 0 0 0 -2.97 -2.97l-8.415 8.385v3h3z" /><path d="M16 5l3 3" /><path d="M9 7.07a7.002 7.002 0 0 0 1 13.93a7.002 7.002 0 0 0 6.929 -5.999" /></svg>' +
                        '</a>' +
                        '<a href="javascript:void(0);" class="text-danger" style="margin-left: 8px;" data-trash="'+category.id+'">' +
                        '<svg xmlns="http://www.w3.org/2000/svg" class="icon" width="24" height="24" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><line x1="4" y1="7" x2="20" y2="7" /><line x1="10" y1="11" x2="10" y2="17" /><line x1="14" y1="11" x2="14" y2="17" /><path d="M5 7l1 12a2 2 0 0 0 2 2h8a2 2 0 0 0 2 -2l1 -12" /><path d="M9 7v-3a1 1 0 0 1 1 -1h4a1 1 0 0 1 1 1v3" /></svg>' +
                        '</a>' +
                        '</td>'
                    html += '</tr>';
                }
            } else {
                html += '<tr><td colspan="3">无数据</td></tr>';
            }
            categoryContainer.innerHTML = html;
            bindEvent();
        }
    });
}


function bindEvent() {
    for(const ele of document.querySelectorAll('[data-trash]')) {
        ele.addEventListener('click', function() {
            Swal.fire({
                title: '您确定吗?',
                text: '确定要移除该分类吗?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: '确定',
                cancelButtonText: '取消'
            }).then((result) => {
                if(result.value) {
                    let id = this.dataset.trash;
                    $.ajax({
                        url: rootPath + 'api/category/' + id,
                        method: 'DELETE',
                        dataType: 'json',
                        success: function() {
                            loadCategories();
                        }
                    })
                }
            });
        });
    }

    for(const ele of document.querySelectorAll('[data-edit]')) {
        ele.addEventListener('click', function(){
            let id = this.dataset.edit;
            let name = this.dataset.name;
            (async () => {
                const { value: name } = await Swal.fire({
                    title: '分类名称',
                    input: 'text',
                    inputLabel: '',
                    inputValue: value,
                    inputPlaceholder: '分类名称',
                    confirmButtonText: '确定'
                })

                if (name) {
                    $.ajax({
                        url: rootPath + 'api/category/' + id,
                        method: 'PATCH',
                        contentType: 'application/json;charset=utf8',
                        dataType: 'json',
                        data: JSON.stringify({'name': value}),
                        success: function (res) {
                            loadCategories();
                        }
                    });
                }
            })()
        });
    }
}