# Job Search

## 功能介紹
### Search Type 
1. `General Search` - 一般查詢<br>
只要打工作相關關鍵字即可查到，可進一步查看詳細的工作內容
2. `Weight Search` - 權重查詢<br>
輸入欲查詢的「關鍵字個數」、「關鍵字」、「權重」後，可依照權重排序查詢結果。
### Search Engine
可以使用不同的搜尋引擎搜尋關鍵字
1. `Google`
2. `Bing`
3. `Yahoo!`
### Else Functions
1. Instant Search - 在首頁輸入工作相關關鍵字後，會自動推薦相關的關鍵字
2. 自定義 4xx 以及 5xx 錯誤處理頁面
3. RWD 響應式網頁設計

## URL Endpoints
1. `/` 首頁 (General Search)

2. `/jobs` 工作列表<br>
HTTP Method: GET<br>
Param: keyword, weight(optional)
3. `/jobs/{id}` 單一工作詳細內容<br>
4. `/search-engine` 設定搜尋引擎 (供 Ajax 使用)<br>
HTTP Method: POST<br>
Param: engine
5. `/api/keyword/lcs` Instant Search 的排序 (供 Ajax 使用)<br>
HTTP Method: POST<br>
Param: keyword