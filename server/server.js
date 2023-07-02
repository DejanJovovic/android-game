const express = require('express');
const app = express();
const socket = require('socket.io');
const server = require('http').createServer(app);
const io = socket(server);
server.listen(3000, () => console.log('server running on port 3000'));

const rooms = [];


io.on('connection', (socket) => {
    console.log("connected");
    socket.emit("connected");
    socket.on("createRoom", () => { 
        let roomCreated = false;
        let count = 0;
        while (!roomCreated && count < 10) {
            const roomId = Math.random().toString(36).substring(2, 7);
            if (rooms.findIndex(r => r.roomId === roomId) !== -1) {
                count++;
            } else {
                rooms.push({roomId: roomId, host: socket.id});
                socket.join(roomId);
                console.log("Room created: " + roomId);
                socket.emit("roomCreated", roomId);
                roomCreated = true;
            }
        }
    });
    
    socket.on("joinRoom", (roomId) => {
        console.log("Room joined: " + roomId);
        if (rooms.findIndex(r => r.roomId === roomId) !== -1) {
            socket.join(roomId);
            socket.emit("roomJoined", roomId);
            io.to(roomId).emit("startGame");
        } else {
            socket.emit("roomNotFound");
        }
    });

    socket.on("destroyRoom", roomId => {
        console.log("Room destroyed: " + roomId);
        if (rooms.findIndex(r => r.roomId === roomId) !== -1) {
            rooms.splice(rooms.findIndex(r => r.roomId === roomId), 1);
            io.to(roomId).emit("roomDestroyed");
        }
    });

    socket.on("mojBroj_generateNumber", (roomId, num, idx) => {
        io.to(roomId).emit("mojBroj_numberGenerated", num, idx);
    })
});



