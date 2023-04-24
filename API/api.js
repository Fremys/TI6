const express = require('express');
const { spawn } = require('child_process');

const app = express();
const port = 8081;

app.get('/sendServer/:server/:file', (req, res) => {
  // Substitua este caminho pelo caminho do seu arquivo Java
  const javaFile = './Client.java';

  // Compila o arquivo Java
  const javac = spawn('javac', [javaFile]);
  javac.on('exit', (code) => {
    if (code !== 0) {
      res.status(500).send('Erro de compilação');
      return;
    }

    // Executa o arquivo Java
    const java = spawn('java', ['Client', req.params.server, req.params.file], );
    java.stdout.on('data', (data) => {
      res.send(data.toString());
      console.log("requisição feita com sucesso");
    });
  });
  
});

app.listen(port, () => {
  console.log(`API executando na porta ${port}`);
});
