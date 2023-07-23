window.onload = function () {
    tinymce.init({
        selector: 'textarea#content',
        min_height: 500,
        max_height: 750,
        menubar: false,
        paste_as_text: true,
        fullpage_default_font_size: "14px",
        branding: false,
        relative_urls: false,
        images_upload_handler: imageUploadHandler,
        plugins: "autolink code link autoresize paste contextmenu image preview",
        toolbar: "undo redo | link image code | fontsizeselect | forecolor | bold italic strikethrough underline | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent",
    })
}

function getCsrfToken() {
    var form = document.getElementById('form')
    if (!form) {
        console.error('Form element not found.')
        return null
    }

    var csrfInput = form.querySelector('[name="_csrf"]')
    if (!csrfInput) {
        console.error('CSRF token input element not found.')
        return null
    }

    return csrfInput.value
}

function getArticleToken() {
    var articleTokenInput = document.getElementById('articleToken')
    if (!articleTokenInput) {
        return null
    }

    return articleTokenInput.value
}

function imageUploadHandler(blobInfo, progress) {
    return new Promise((resolve, reject) => {
        var url = '/api/images/upload'
        var formData = new FormData()
        formData.append('_csrf', getCsrfToken())
        formData.append('image', blobInfo.blob(), blobInfo.filename())
        var articleToken = getArticleToken()
        if (articleToken) {
            formData.append('articleToken', articleToken)
        }

        fetch(url, {
            method: 'POST',
            body: formData,
            credentials: 'include',
        }).then(response => {
            if (!response.ok) {
                if (response.status === 403) {
                    reject('HTTP Error: ' + response.status, {remove: true})
                } else {
                    reject('HTTP Error: ' + response.status)
                }
                return
            }
            return response.json()
        }).then(json => {
            if (!json || typeof json.location !== 'string') {
                reject('Invalid JSON: ' + JSON.stringify(json))
                return
            }
            resolve(json.location)
        }).catch(error => {
            reject('Image upload failed due to a fetch error: ' + error.message)
        })
    })
}
