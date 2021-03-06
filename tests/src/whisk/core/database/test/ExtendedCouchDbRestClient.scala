/*
 * Copyright 2015-2016 IBM Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package whisk.core.database.test

import scala.concurrent.Future

import akka.actor.ActorSystem
import akka.http.scaladsl.model._

import spray.json._

import whisk.core.database.CouchDbRestClient

/**
 * Implementation of additional endpoints that should only be used in testing.
 */
class ExtendedCouchDbRestClient(protocol: String, host: String, port: Int, username: String, password: String, db: String)(implicit system: ActorSystem)
    extends CouchDbRestClient(protocol, host, port, username, password, db) {

    // http://docs.couchdb.org/en/1.6.1/api/server/common.html#get--
    def instanceInfo(): Future[Either[StatusCode, JsObject]] =
        requestJson(mkRequest(HttpMethods.GET, Uri./))

    // http://docs.couchdb.org/en/1.6.1/api/database/common.html#put--db
    def createDb(): Future[Either[StatusCode, JsObject]] =
        requestJson(mkRequest(HttpMethods.PUT, uri(db)))

    // http://docs.couchdb.org/en/1.6.1/api/database/common.html#delete--db
    def deleteDb(): Future[Either[StatusCode, JsObject]] =
        requestJson(mkRequest(HttpMethods.DELETE, uri(db)))
}
