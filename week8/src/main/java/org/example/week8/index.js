window.addEventListener('load', function(evt) {


    const output = this.document.querySelector('#output');

    output.textContent = '便引诗情到碧霄';

    const socket = new WebSocket('ws://localhost:54000');
    socket.onmessage = (evt) => {
        output.textContent = evt.data;
    };


    const d = new Date();

    socket.addEventListener("open", (evt) => {
        const msg = {
            id: 1234, 
            name: "Alice", 
            date: d.toISOString().split('T')[0], 
            isPremium: true
        };

        socket.send(JSON.stringify(msg));
    });
})