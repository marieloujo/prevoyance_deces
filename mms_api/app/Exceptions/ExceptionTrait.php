<?php
namespace App\Exceptions;

use Symfony\Component\HttpFoundation\Response;
use Illuminate\Database\Eloquent\ModelNotFoundException;
use Illuminate\Session\TokenMismatchException;
use Illuminate\Validation\ValidationException;
use Symfony\Component\HttpKernel\Exception\NotFoundHttpException;

trait ExceptionTrait{

    public function apiExceptions($request,$e){

        if($this->isModel($e)){

            return $this->ModelResponse($e);
        }

        if($this->isHttp($e)){

            return $this->HttpResponse($e);
        }

        if($this->isAuthentication($e)){

            return $this->AuthenticationResponse($e);
        }

        if($this->isValidation($e,$request)){

             return $this->ValidationResponse($e,$request);
        }

        if($this->isTokenMismatch($e)){

             return $this->TokenMismatchResponse($e);
        }
        
        return parent::render($request, $e);
    }

    protected function isModel($e){
        return $e instanceof ModelNotFoundException;
    }

    protected function isHttp($e){
        return $e instanceof NotFoundHttpException;
    }

    protected function isAuthentication($e){
        return $e instanceof AuthenticationException;
    }

    protected function isValidation($e){
        return $e instanceof ValidationException;
    }

    protected function isTokenMismatch($e){
        return $e instanceof TokenMismatchException;
    }
    
    protected function ModelResponse($e){
        return response()->json([
            "errors" => 'Aucun resultat'
        ],Response::HTTP_NOT_FOUND);
    }

    protected function HttpResponse($e){
        return response()->json([
            "errors" => 'Chemin incorrecte'
        ],Response::HTTP_NOT_FOUND);
    }

    protected function AuthenticationResponse($e){
        return response()->json([
            "errors" => 'Pas connecte'
        ],Response::HTTP_UNAUTHORIZED);
    }

    protected function ValidationResponse($e,$request){
        $errors = $e->validator->errors()->getMessages();
        return response()->json([
            "errors" => $errors
        ],Response::HTTP_UNPROCESSABLE_ENTITY);
    }

    protected function TokenMismatchResponse($e){
        return response()->json([
            "errors" => 'Mauvaise http request méthode'
        ],Response::HTTP_NOT_FOUND);
    }

}
