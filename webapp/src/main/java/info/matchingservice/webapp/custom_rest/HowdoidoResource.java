/*
 * Copyright 2015 Yodo Int. Projects and Consultancy
 *
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package info.matchingservice.webapp.custom_rest;

import info.matchingservice.dom.CommunicationChannels.CommunicationChannels;
import info.matchingservice.dom.Howdoido.*;
import info.matchingservice.dom.Match.ProfileMatches;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.viewer.restfulobjects.applib.JsonRepresentation;
import org.apache.isis.viewer.restfulobjects.applib.RestfulMediaType;
import org.apache.isis.viewer.restfulobjects.applib.client.RestfulResponse;
import org.apache.isis.viewer.restfulobjects.rendering.RestfulObjectsApplicationException;
import org.apache.isis.viewer.restfulobjects.server.resources.ResourceAbstract;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by jodo on 15/05/15.
 */
@Path("/hdid")
public class HowdoidoResource extends ResourceAbstract {


    @GET
    @Path("/")
    @Produces({ MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR })
    public Response getActivePersonServices()  {

        final Api api = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Api.class);

        BasicUser activeUser = api.activeUser();

        return Response.status(200).entity(basicUserRepresentation(activeUser).toString()).build();
    }

//    @GET
//    @Path("/people/{instanceId}")
//    @Produces({ MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR })
//    public Response getPeopleServices(@PathParam("instanceId") String instanceId)  {
//
//        final Persons persons = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Persons.class);
//
//        Person activePerson = persons.matchPersonApiId(instanceId);
//
//        return Response.status(200).entity(personRepresentation(activePerson).toString()).build();
//    }

    @DELETE
    @Path("/")
    public Response deleteServicesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Deleting the v1 resource is not allowed.", new Object[0]);
    }

    @POST
    @Path("/")
    public Response postServicesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Posting to the v1 resource is not allowed.", new Object[0]);
    }

    @PUT
    @Path("/")
    public Response putServicesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Putting to the v1 resource is not allowed.", new Object[0]);
    }

    @DELETE
    @Path("/people/{instanceId}")
    public Response deletePeopleNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Deleting the people resource is not allowed.", new Object[0]);
    }

    @POST
    @Path("/people/{instanceId}")
    public Response postPeopleNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Posting to the people resource is not allowed.", new Object[0]);
    }

    @PUT
    @Path("/people/{instanceId}")
    public Response putPeopleNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Putting to the people resource is not allowed.", new Object[0]);
    }

    private JsonRepresentation basicUserRepresentation(BasicUser basicUser){

        JsonRepresentation all = JsonRepresentation.newMap();

        // activeperson
        JsonRepresentation activeUser = JsonRepresentation.newMap();
        activeUser.mapPut("id", Utils.toApiID(basicUser.getOID()));
        activeUser.mapPut("URI", Utils.toObjectURI(basicUser.getOID()));
        activeUser.mapPut("name", basicUser.getName());
        activeUser.mapPut("email", basicUser.getEmail());
        activeUser.mapPut("title", basicUser.title());

        // templates
        JsonRepresentation templates = JsonRepresentation.newArray();
        for (BasicTemplate template : basicUser.getMyTemplates()) {
            templates.arrayAdd(basicTemplateRepresentation(template));
        }
        activeUser.mapPut("templates", templates);

        // feedback receivedRequests
        JsonRepresentation receivedRequests = JsonRepresentation.newArray();
        for (BasicRequest request : basicUser.getReceivedFeedbackRequests()) {
            receivedRequests.arrayAdd(basicRequestRepresentation(request));
        }
        activeUser.mapPut("receivedRequests", receivedRequests);

        // given feedback
        JsonRepresentation givenFeedback = JsonRepresentation.newArray();
        for (BasicForm form : basicUser.getGivenFeedback()) {
            givenFeedback.arrayAdd(basicFormRepresentation(form));
        }
        activeUser.mapPut("givenFeedback", givenFeedback);

        // given ratings
        JsonRepresentation givenRatings = JsonRepresentation.newArray();
        for (BasicRating rating : basicUser.getMyGivenRatings()) {
            givenRatings.arrayAdd(basicRatingRepresentation(rating));
        }
        activeUser.mapPut("givenRatings", givenRatings);

        // received ratings
        JsonRepresentation receivedRatings = JsonRepresentation.newArray();
        for (BasicRating rating : basicUser.getMyReceivedRatings()) {
            receivedRatings.arrayAdd(basicRatingRepresentation(rating));
        }
        activeUser.mapPut("receivedRatings", receivedRatings);

        // received feedback
        JsonRepresentation receivedFeedback = JsonRepresentation.newArray();
        for (BasicForm form : basicUser.getReceivedFeedback()) {
            receivedFeedback.arrayAdd(basicFormRepresentation(form));
        }
        activeUser.mapPut("receivedFeedback", receivedFeedback);

        // And finally: wrap it all up...
        all.mapPut("user",activeUser);

        return all;

    }

    private JsonRepresentation basicTemplateRepresentation(BasicTemplate template ) {

        JsonRepresentation templatemap = JsonRepresentation.newMap();

        templatemap.mapPut("id", Utils.toApiID(template.getOID()));
        templatemap.mapPut("URI", Utils.toObjectURI(template.getOID()));
        templatemap.mapPut("name", template.getName());
        templatemap.mapPut("category", template.getBasicCategory().getName());

        JsonRepresentation questions = JsonRepresentation.newArray();
        for (BasicQuestion question : template.getBasicQuestions()) {
            questions.arrayAdd(basicQuestionRepresentation(question));
        }
        templatemap.mapPut("questions",questions);

        return templatemap;
    }

    private JsonRepresentation basicQuestionRepresentation(BasicQuestion question) {

        JsonRepresentation questionmap = JsonRepresentation.newMap();

        questionmap.mapPut("question", question.getBasicQuestion());
        questionmap.mapPut("formType", question.getBasicFormType().toString());

        return questionmap;
    }

    private JsonRepresentation basicRequestRepresentation(BasicRequest request) {

        JsonRepresentation requestmap = JsonRepresentation.newMap();

        requestmap.mapPut("id", Utils.toApiID(request.getOID()));
        requestmap.mapPut("URI", Utils.toObjectURI(request.getOID()));
        requestmap.mapPut("requestOwnerName", request.getRequestOwner().getName());
        requestmap.mapPut("requestOwnerEmail", request.getRequestOwner().getEmail());
        requestmap.mapPut("dateTime", request.getDateTime().toString());
        requestmap.mapPut("requestTemplateURI", Utils.toObjectURI(request.getBasicTemplate().getOID()));
        JsonRepresentation questions = JsonRepresentation.newArray();
        for (BasicQuestion question : request.getBasicTemplate().getBasicQuestions()) {
            questions.arrayAdd(basicQuestionRepresentation(question));
        }
        requestmap.mapPut("questions",questions);

        return requestmap;
    }

    private JsonRepresentation basicFormRepresentation(BasicForm form) {

        JsonRepresentation formmap = JsonRepresentation.newMap();

        formmap.mapPut("id", Utils.toApiID(form.getOID()));
        formmap.mapPut("URI", Utils.toObjectURI(form.getOID()));
        formmap.mapPut("formCreator", form.getFormCreator().title());
        formmap.mapPut("formCreatorId", Utils.toApiID(form.getFormCreator().getOID()));
        formmap.mapPut("formCreatorURI", Utils.toObjectURI(form.getFormCreator().getOID()));
        formmap.mapPut("formReceiver", form.getFormReceiver().title());
        formmap.mapPut("formReceiverId", Utils.toApiID(form.getFormReceiver().getOID()));
        formmap.mapPut("formReceiverURI", Utils.toObjectURI(form.getFormReceiver().getOID()));
        formmap.mapPut("basicTemplateId", Utils.toApiID(form.getBasicTemplate().getOID()));
        formmap.mapPut("basicTemplateURI", Utils.toObjectURI(form.getBasicTemplate().getOID()));
        formmap.mapPut("dateTime", form.getDateTime().toString());
        formmap.mapPut("formRated", form.getFormRated());
        formmap.mapPut("anonymous", form.isAnonymous());
        formmap.mapPut("published", form.getPublished());
        JsonRepresentation answers = JsonRepresentation.newArray();
        for (BasicAnswer answer : form.getAnswers()) {
            answers.arrayAdd(basicAnswerRepresentation(answer));
        }
        formmap.mapPut("answers", answers);

        return formmap;
    }

    private JsonRepresentation basicAnswerRepresentation(BasicAnswer answer) {

        JsonRepresentation answermap = JsonRepresentation.newMap();

        answermap.mapPut("title", answer.title().toString());
        answermap.mapPut("questionToAnswer", answer.getQuestionToAnswer().toString());
        answermap.mapPut("matchingTrustlevel", answer.getMatchingTrustlevel().toString());
        try {
            BasicAnswerRating answerRating = (BasicAnswerRating) answer;
            answermap.mapPut("rating", answerRating.getRating());

        } catch (Exception e) {
            // do nothing
        }
        try {
            BasicAnswerRatingExplanation answerRatingExplanation = (BasicAnswerRatingExplanation) answer;
            answermap.mapPut("rating", answerRatingExplanation.getRatingValue());
            answermap.mapPut("explanation", answerRatingExplanation.getExplanation().toString());

        } catch (Exception e) {
            // do nothing
        }
        return answermap;
    }

    private JsonRepresentation basicRatingRepresentation(BasicRating rating) {

        JsonRepresentation ratingmap = JsonRepresentation.newMap();

        ratingmap.mapPut("dateTime", rating.getDateTime().toString());
        ratingmap.mapPut("category", rating.getBasicCategory().getName());
        ratingmap.mapPut("feedbackRating", rating.getFeedbackRating().toString());
        ratingmap.mapPut("creator", rating.getCreator().title().toString());
        ratingmap.mapPut("creatorId", Utils.toApiID(rating.getCreator().getOID()));
        ratingmap.mapPut("creatorURI", Utils.toObjectURI(rating.getCreator().getOID()));
        ratingmap.mapPut("receiver", rating.getReceiver().title().toString());
        ratingmap.mapPut("receiverId", Utils.toApiID(rating.getReceiver().getOID()));
        ratingmap.mapPut("receiverURI", Utils.toObjectURI(rating.getReceiver().getOID()));

        return ratingmap;
    }

    private CommunicationChannels communicationChannels = IsisContext.getPersistenceSession().getServicesInjector().lookupService(CommunicationChannels.class);
    private ProfileMatches profileMatches = IsisContext.getPersistenceSession().getServicesInjector().lookupService(ProfileMatches.class);
}