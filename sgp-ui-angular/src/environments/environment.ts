const protocolo = 'http://';
const protocoloWebSocket = 'ws://';

const ip = 'localhost:8080';

export const environment = {
 production: false,
 ip: ip,
 apiUrl: protocolo + ip,
 webSocket: protocoloWebSocket + ip

};