/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.frontendtemplateprovider.controllers

import play.api.Play
import play.api.Play.current
import play.api.mvc._
import play.twirl.api.Html
import uk.gov.hmrc.play.microservice.controller.BaseController

import scala.concurrent.Future

object GovUkTemplateRenderer extends GovUkTemplateRenderer

trait GovUkTemplateRenderer extends BaseController {

	def hello(): Action[AnyContent] = Action.async { implicit request =>
		Future.successful(Ok(Html("Hello world")))
	}

	def serveMustacheTemplate(): Action[AnyContent] = Action.async { implicit request =>
		val mustacheInputStream = Play.resourceAsStream("gov_main.mustache").get
		val mustacheString = scala.io.Source.fromInputStream(mustacheInputStream).mkString
		val assetsPrefix = uk.gov.hmrc.play.config.AssetsConfig.assetsPrefix
		val mustache = mustacheString.replace("{{assetPath}}", assetsPrefix)
		Future.successful(Ok(mustache))
	}

}