import './App.css'
import { BrowserRouter as Router } from 'react-router-dom'
import { getApps } from './utils/helper'

function App() {
  const CurrentApp = getApps();
  return (
     /*BrowserRouter needs when we add routing and it is parent component of library react-router-dom.*/
      <Router>
        <CurrentApp />
      </Router>
  )
}

export default App
