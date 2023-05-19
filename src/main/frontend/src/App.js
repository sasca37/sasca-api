import React, {useEffect, useState} from 'react';
import axios from 'axios';

function App() {
  const [hello, setHello] = useState('')
  useEffect(() => {
    axios.get('/api/hello')
        .then(response => setHello(response.data))
        .catch(error => console.log(error))
  }, []);

/*
  display: flex;
  color: white;
  padding: 20px;
  font-weight: 600;
  font-size: 20px;
 */

  return (

    <div>
        <div className="bg-black w-full flex p-0.5 font-semibold text-xl" >
          <div style={ {color: 'blue'}}>개발 Blog</div>
        </div>
    <div>{hello}</div>
    <h1 className="text-3xl font-bold underline">
    Hello world!
    </h1>
    <button className="border-solid border-2 border-indigo-600">buttonA</button>
    <button className="border-dashed border-2 border-indigo-600">buttonA</button>
    <button className="border-dotted border-2 border-indigo-600">buttonA</button>
    <button className="border-double border-4 border-indigo-600">buttonA</button>
    </div>
  );
}

export default App;