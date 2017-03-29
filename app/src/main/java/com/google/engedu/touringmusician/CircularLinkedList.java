/* Copyright 2016 Google Inc.
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

package com.google.engedu.touringmusician;


import android.graphics.Point;

import java.util.Iterator;

public class  CircularLinkedList implements Iterable<Point> {

    private class Node {
        Point point;
        Node prev, next;

        Node(){}

        Node(Node n){
            point = n.getPoint();
            prev = n.getPrev();
            next = n.getNext();
        }

        void setPrev (Node n){
            prev = n;
        }

        void setNext (Node n){
            next = n;
        }

        void setPoint (Point p){
            point = p;
        }

        Node getPrev(){
            return prev;
        }

        Node getNext(){
            return next;
        }

        Point getPoint(){
            return point;
        }
    }

    Node head;

    public void insertBeginning(Point p) {
        if (head == null){
            Node newNode = new Node();
            newNode.setNext(newNode);
            newNode.setPrev(newNode);
            newNode.setPoint((p));
            head = newNode;
        }
        else if (head.getNext() == head)
        {
            Node newNode = new Node();
            newNode.setNext(head);
            newNode.setPrev(head);
            newNode.setPoint(p);

            head.setNext(newNode);
            head.setPrev(newNode);
            head = newNode;

        }
        else
        {
            Node current = head.getPrev();

            Node newNode = new Node();
            newNode.setPoint(p);
            newNode.setNext(head);
            newNode.setPrev(current);

            current.setNext(newNode);

            head.setPrev(newNode);

            head = newNode;
        }


    }

    private float distanceBetween(Point from, Point to) {
        return (float) Math.sqrt(Math.pow(from.y-to.y, 2) + Math.pow(from.x-to.x, 2));
    }

    public float totalDistance() {
        float total = 0;

        Node current = head;
        do{
            total += distanceBetween(current.point, current.getNext().getPoint());
            current = current.getNext();
        }
        while(current.getNext() != head.getNext());

        return total;
    }

    public void insertNearest(Point p) {
        Node current = head;
        Node nearest = null;
        float distance = Float.POSITIVE_INFINITY;

        if (head == null)
        {
            Node newNode = new Node();
            newNode.setNext(newNode);
            newNode.setPrev(newNode);
            newNode.setPoint((p));
            head = newNode;
        }
        else {
            do {
                if (distanceBetween(current.getPoint(), p) < distance) {
                    distance = distanceBetween(current.getPoint(), p);
                    nearest = current;
                    current = current.getNext();
                }
                else{
                    current = current.getNext();
                }
            }
            while (current != head);

            Node newNode = new Node();
            newNode.setPoint(p);
            newNode.setNext(nearest.getNext());
            newNode.setPrev(nearest);
            nearest.getNext().setPrev(newNode);
            nearest.setNext(newNode);
        }
    }

    public void insertSmallest(Point p) {
        Node current = head;
        Node shortest = null;
        float distance = Float.POSITIVE_INFINITY;

        if (head == null)
        {
            Node newNode = new Node();
            newNode.setNext(newNode);
            newNode.setPrev(newNode);
            newNode.setPoint((p));
            head = newNode;
        }
        else if (head.getNext() == head)
        {
            Node newNode = new Node();
            newNode.setNext(head);
            newNode.setPrev(head);
            newNode.setPoint(p);

            head.setNext(newNode);
            head.setPrev(newNode);
            head = newNode;
        }
        else
        {
            do{
                if (distanceBetween(current.getPoint(), p) +
                        distanceBetween(current.getNext().getPoint(), p) < distance)
                {
                    distance = distanceBetween(current.getPoint(), p) +
                            distanceBetween(current.getNext().getPoint(), p);
                    shortest = current;
                    current = current.getNext();
                }
                else
                {
                    current = current.getNext();
                }
            }
            while (current != head);
            Node newNode = new Node();
            newNode.setPoint(p);
            newNode.setNext(shortest.getNext());
            newNode.setPrev(shortest);
            shortest.getNext().setPrev(newNode);
            shortest.setNext(newNode);
        }
    }

    public void reset() {
        head = null;
    }

    private class CircularLinkedListIterator implements Iterator<Point> {

        Node current;

        public CircularLinkedListIterator() {
            current = head;
        }

        @Override
        public boolean hasNext() {
            return (current != null);
        }

        @Override
        public Point next() {
            Point toReturn = current.point;
            current = current.next;
            if (current == head) {
                current = null;
            }
            return toReturn;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Iterator<Point> iterator() {
        return new CircularLinkedListIterator();
    }


}
