

function sendDoc(id, checked) {
    const data = {
        'id': id,
        'clicked': checked
    }

    console.log("post", data)

    axios.post('/admin/documents', data).then( response =>
        console.log('success:' + response.data))

}

