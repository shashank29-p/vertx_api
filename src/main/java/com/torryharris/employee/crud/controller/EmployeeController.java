package com.torryharris.employee.crud.controller;

import com.torryharris.employee.crud.dao.Dao;
import com.torryharris.employee.crud.dao.impl.EmployeeJdbcDao;
import com.torryharris.employee.crud.model.Employee;
import com.torryharris.employee.crud.model.Response;
import com.torryharris.employee.crud.util.Utils;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.impl.RoutingContextDecorator;
import io.vertx.sqlclient.Row;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static io.vertx.core.Future.future;

public class EmployeeController {
  private static final Logger LOGGER = LogManager.getLogger(EmployeeController.class);
  private final Dao<Employee> employeeDao;

  public EmployeeController(Vertx vertx) {
    employeeDao = new EmployeeJdbcDao(vertx);
  }

  public Promise<Response> getEmployees() {
    Promise<Response> responsePromise = Promise.promise();
    Response response = new Response();
    employeeDao.getAll()
      .future()
      .onSuccess(employees -> {
        response.setStatusCode(200)
          .setResponseBody(Json.encode(employees));
        responsePromise.tryComplete(response);
      })
      .onFailure(throwable -> {
        response.setStatusCode(500)
          .setResponseBody(Utils.getErrorResponse(throwable.getMessage()).toString());
        responsePromise.tryComplete(response);
        LOGGER.catching(throwable);
      });
    return responsePromise;
  }

  public Promise<Response> getEmployeebyId(String id) {
    Promise<Response> responsePromise = Promise.promise();
    Response response = new Response();
    LOGGER.info(id);
    employeeDao.get(id)
      .future()
      .onSuccess(employees -> {
        response.setStatusCode(200)
          .setResponseBody(Json.encode(employees));
        responsePromise.tryComplete(response);
      })
      .onFailure(throwable -> {
        response.setStatusCode(500)
          .setResponseBody(Utils.getErrorResponse(throwable.getMessage()).toString());
        responsePromise.tryComplete(response);
        LOGGER.catching(throwable);
      });
    return responsePromise;
  }

  public Promise<Response> postEmployee(Employee employee) {
    Promise<Response> responsePromise = Promise.promise();
    List<Employee> employeeList = new ArrayList<>();
    Response responsee = new Response();
    employeeDao.save(employee);

    return responsePromise;
  }

  public Promise<Response> putEmployee(Employee employee) {
    Promise<Response> responsePromise = Promise.promise();
    List<Employee> employeeList = new ArrayList<>();
    Response responsee = new Response();
    employeeDao.update(employee);

    return responsePromise;
  }

  public Promise<Response> deleteEmployee(String id) {
    Promise<Response> responsePromise = Promise.promise();
    Response response = new Response();
    LOGGER.info(id);
    employeeDao.delete(id);

    return responsePromise;
  }
  public Promise<Response> login(String username,String password) {
    Promise<Response> responsePromise = Promise.promise();
    Response response = new Response();
    employeeDao.login(username,password)
      .future()
      .onSuccess(employees -> {
        response.setStatusCode(200)
          .setResponseBody(Json.encode(employees));
        responsePromise.tryComplete(response);

      })
      .onFailure(throwable -> {
        response.setStatusCode(500)
          .setResponseBody(Utils.getErrorResponse(throwable.getMessage()).toString());
       responsePromise.tryComplete(response);
        LOGGER.catching(throwable);
      });
    return responsePromise;
  }
}
