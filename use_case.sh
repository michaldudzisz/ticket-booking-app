echo 'Fetching available screening in date of 30.06.2021...\n\n'

echo $(curl -s "http://localhost:8080/book/list-screenings?from=2021-06-30T08:00:00&to=2021-06-30T23:00:00")

echo '\n\nGoing for Ranczo Wilkowyje as best movie - fetching available seats...\n\n'

echo $(curl -s "http://localhost:8080/book/available-seats/6")

echo '\n\nGoing to book 2 tickets - child and adult.\n'
echo 'Making reservation. Server response: \n\n'

echo $(curl -s --header "Content-Type: application/json" \
      --request POST \
      --data "{\"firstName\":\"Michał\", \
              \"lastName\":\"Dudzisz\", \
              \"screeningId\":6,
              \"seats\":[\
                {\
                    \"rowNr\":2,\
                    \"seatNr\":2\
                },\
                {\
                    \"rowNr\":2,\
                    \"seatNr\":3\
                }\
              ],\
              \"tickets\":[\
                  \"child\",
                  \"adult\"\
              ]\
              }" \
      "http://localhost:8080/book/make-reservation")

echo '\nSeats are booked! \n\n'
echo 'But if I try to book a seat for the same screening, that will left just one seat between already booked... \n\n'

echo $(curl -s --header "Content-Type: application/json" \
      --request POST \
      --data "{\"firstName\":\"Michał\", \
              \"lastName\":\"Dudzisz\", \
              \"screeningId\":6,
              \"seats\":[\
                {\
                    \"rowNr\":2,\
                    \"seatNr\":5\
                }\
              ],\
              \"tickets\":[\
                  \"child\"\
              ]\
              }" \
      "http://localhost:8080/book/make-reservation")

echo '\n\nApp should not allow for such reservation.\n'

