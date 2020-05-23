<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SIMORA - Shto shfrytezues</title>
    <link rel="stylesheet" href="/pages/css/menu.css"/>
    <link rel="stylesheet" href="/pages/css/addUser.css"/>
</head>
<body>
    <div class="menu">
        <div class="header">
            <p>SIMORA</p>
        </div>
        <div class="photo">
            <img src="/pages/images/Avatar.png" width="200px" height="200px">
        </div>
        <div class="info">
            <p>ID</p>
            <p>Emri Mbiemri</p>
            <p>Email</p>
            <p>Departamenti i DEPT</p>
            <p>FSHMN</p>
        </div>
        <div style="margin-top: 125px;" class="menuItems">
            <nav>
                <ul>
                    <li><a href="#"> Orari i provimeve </a></li>
                    <hr>
                    <li> <a href="#">Kontakto profesoret </a></li>
                    <hr>
                    <li> <a href="#">Shto shfrytezues </a></li>
                    <hr>
                </ul>
            </nav>
        </div>
        <div class="footer">
            <a href="#" id="logout">Ckyqu</a>
            <a href="#" id="changePass">Ndrysho fjalekalimin</a>
        </div>
    </div>

    <div class="content">
        <div style="margin-top: 100px;" class="contentTitle">
            <h2>SHTO SHFRYTEZUES</h2>
        </div>
        <div class="addUser">
            <form action="#" method="GET">
                <table>
                    <tr>
                        <td>
                            <label>Emri:</label>

                        </td>
                        <td>
                            <input type="text">


                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>Mbiemri:</label>
                        </td>
                        <td>
                            <input type="text">
                        </td>
                    </tr>
                    <tr>
                        <td><label>ID: </label></td>
                        <td><input type="text"></td>
                    </tr>
                    <tr>
                        <td><label>Departamenti:</label></td>
                        <td>
                            <select>
                                <option>Matematike</option>
                                <option>Fizike</option>
                                <option>Kimi</option>
                                <option>Biologji</option>
                                <option>Gjeografi</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td><label>Emaili: </label></td>
                        <td><input type="text"></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td >
                            <input type="submit" value="SHTO">
                        </td>
                    </tr>

                </table>
            </form>
        </div>
    </div>
</body>
</html>